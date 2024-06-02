package com.kotlity.notificationpractise.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.kotlity.notificationpractise.presentation.MessageToastInfo
import com.kotlity.notificationpractise.presentation.composables.IdleMessageNotificationScreenState
import com.kotlity.notificationpractise.presentation.composables.NotificationPermissionRationaleDialog
import com.kotlity.notificationpractise.presentation.event.NotificationEvent
import com.kotlity.notificationpractise.presentation.state.NotificationScreenState
import com.kotlity.notificationpractise.presentation.state.MessageStatus
import com.kotlity.notificationpractise.utils.Constants.POST_NOTIFICATIONS_PERMISSION
import com.kotlity.notificationpractise.utils.getActivity
import com.kotlity.notificationpractise.utils.isGreaterOrEqual
import com.kotlity.notificationpractise.utils.onPermissionDenied
import com.kotlity.notificationpractise.utils.requestPermission
import com.kotlity.notificationpractise.utils.showSnackbar
import kotlinx.coroutines.flow.Flow

@SuppressLint("InlinedApi")
@Composable
fun MessageNotificationScreen(
    notificationScreenState: NotificationScreenState,
    messageToastFlow: Flow<MessageToastInfo>,
    onEvent: (NotificationEvent) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val postNotificationPermissionManager = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isPermissionGranted ->
            if (isPermissionGranted) onEvent(NotificationEvent.OnSendMessage)
            else {
                context.getActivity()?.let {
                    onPermissionDenied(
                        activity = it,
                        permission = POST_NOTIFICATIONS_PERMISSION,
                        onShowPermissionRationale = {
                            onEvent(NotificationEvent.OnUpdateNotificationPermissionRationaleDialog)
                        },
                        onRequestPermissionAgain = {
                            requestPermission(
                                activity = it,
                                permission = POST_NOTIFICATIONS_PERMISSION
                            )
                        }
                    )
                }
            }
        }
    )

    if (notificationScreenState.isNotificationPermissionRationaleVisible) {
        NotificationPermissionRationaleDialog(
            onDismiss = {
                onEvent(NotificationEvent.OnUpdateNotificationPermissionRationaleDialog)
            },
            onConfirmButtonClick = {
                onEvent(NotificationEvent.OnUpdateNotificationPermissionRationaleDialog)
                postNotificationPermissionManager.launch(POST_NOTIFICATIONS_PERMISSION)
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        messageToastFlow.flowWithLifecycle(lifecycleOwner.lifecycle).collect { messageToastInfo ->
            when(messageToastInfo) {
                is MessageToastInfo.Error -> snackbarHostState.showSnackbar(uiText = messageToastInfo.error, context = context)
                is MessageToastInfo.Success -> snackbarHostState.showSnackbar(uiText = messageToastInfo.message, context = context)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Crossfade(
                targetState = notificationScreenState.messageStatus,
                label = ""
            ) { messageStatus ->
                when(messageStatus) {
                    MessageStatus.Idle -> IdleMessageNotificationScreenState(
                        message = notificationScreenState.message,
                        notificationMessage = notificationScreenState.notificationMessage,
                        onMessageChange = {
                            onEvent(NotificationEvent.OnUpdateMessage(it))
                        },
                        onSendButtonClick = {
                            if (isGreaterOrEqual(Build.VERSION_CODES.TIRAMISU)) {
                                postNotificationPermissionManager.launch(POST_NOTIFICATIONS_PERMISSION)
                            } else {
                                onEvent(NotificationEvent.OnSendMessage)
                            }
                        }
                    )
                    MessageStatus.Loading -> CircularProgressIndicator()
                }
            }
        }
    }
}