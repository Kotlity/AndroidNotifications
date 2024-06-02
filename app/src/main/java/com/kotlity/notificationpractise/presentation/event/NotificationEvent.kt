package com.kotlity.notificationpractise.presentation.event

sealed interface NotificationEvent {
    data class OnUpdateMessage(val message: String): NotificationEvent
    data class OnUpdateNotificationMessage(val message: String): NotificationEvent
    data object OnSendMessage: NotificationEvent
    data object OnUpdateNotificationPermissionRationaleDialog: NotificationEvent
}