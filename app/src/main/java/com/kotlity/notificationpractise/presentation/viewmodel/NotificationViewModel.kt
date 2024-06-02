package com.kotlity.notificationpractise.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlity.notificationpractise.domain.MessageRepository
import com.kotlity.notificationpractise.presentation.MessageToastInfo
import com.kotlity.notificationpractise.presentation.event.NotificationEvent
import com.kotlity.notificationpractise.presentation.state.NotificationScreenState
import com.kotlity.notificationpractise.presentation.state.MessageStatus
import com.kotlity.notificationpractise.utils.NotificationError
import com.kotlity.notificationpractise.utils.NotificationSuccess
import com.kotlity.notificationpractise.utils.Response
import com.kotlity.notificationpractise.utils.asErrorUiText
import com.kotlity.notificationpractise.utils.asSuccessUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val messageRepository: MessageRepository<NotificationSuccess, NotificationError>
): ViewModel() {

    var notificationScreenState by mutableStateOf(NotificationScreenState())
        private set

    private val messageToastChannel = Channel<MessageToastInfo>()
    val messageToastFlow = messageToastChannel.receiveAsFlow()

    private var sendMessageJob: Job? = null

    fun onEvent(notificationEvent: NotificationEvent) {
        when(notificationEvent) {
            is NotificationEvent.OnUpdateMessage -> updateMessage(notificationEvent.message)
            is NotificationEvent.OnUpdateNotificationMessage -> updateNotificationMessage(notificationEvent.message)
            is NotificationEvent.OnSendMessage -> sendMessage()
            NotificationEvent.OnUpdateNotificationPermissionRationaleDialog -> updateNotificationPermissionRationaleDialog()
        }
    }

    private fun updateMessage(message: String) {
        notificationScreenState = notificationScreenState.copy(message = message)
    }

    private fun updateNotificationMessage(message: String) {
        notificationScreenState = notificationScreenState.copy(notificationMessage = message)
    }

    private fun sendMessage() {
        sendMessageJob?.cancel()
        val message = notificationScreenState.message
        sendMessageJob = messageRepository.sendMessage(message = message)
            .onEach { response -> response.handleMessageResponse() }
            .launchIn(viewModelScope)
    }

    private fun Response<NotificationSuccess, NotificationError>.handleMessageResponse() {
        when(this) {
            is Response.Error -> {
                setMessageStatus(MessageStatus.Idle)
                sendMessageToastInfoToTheChannel(MessageToastInfo.Error(error = asErrorUiText()))
            }
            is Response.Loading -> setMessageStatus(MessageStatus.Loading)
            is Response.Success -> {
                setMessageStatus(MessageStatus.Idle)
                updateMessage(message = "")
                sendMessageToastInfoToTheChannel(MessageToastInfo.Success(message = asSuccessUiText()))
            }
        }
    }

    private fun setMessageStatus(messageStatus: MessageStatus) {
        notificationScreenState = notificationScreenState.copy(messageStatus = messageStatus)
    }

    private fun sendMessageToastInfoToTheChannel(messageToastInfo: MessageToastInfo) {
        viewModelScope.launch {
            messageToastChannel.send(messageToastInfo)
        }
    }

    private fun updateNotificationPermissionRationaleDialog() {
        notificationScreenState = notificationScreenState.copy(isNotificationPermissionRationaleVisible = !notificationScreenState.isNotificationPermissionRationaleVisible)
    }
}