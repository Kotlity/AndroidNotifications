package com.kotlity.notificationpractise.presentation.state

data class NotificationScreenState(
    val message: String = "",
    val notificationMessage: String = "",
    val isNotificationPermissionRationaleVisible: Boolean = false,
    val messageStatus: MessageStatus = MessageStatus.Idle
)

sealed interface MessageStatus {
    data object Loading: MessageStatus
    data object Idle: MessageStatus
}