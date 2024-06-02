package com.kotlity.notificationpractise.utils

import android.Manifest
import android.annotation.SuppressLint

object Constants {

    const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
    const val NOTIFICATION_CHANNEL_NAME = "Messaging"
    const val NOTIFICATION_CHANNEL_DESCRIPTION = "Used for sending messages"
    const val NOTIFICATION_CONTENT_TITLE = "A new message"
    const val NOTIFICATION_CONTENT_TEXT_CHARACTER_LIMIT = 15
    const val MESSAGE_ACTIVITY_PENDING_INTENT_REQUEST_CODE = 10
    const val LIKE_A_MESSAGE_BROADCAST_PENDING_INTENT_REQUEST_CODE = 20
    const val NOTIFICATION_ID = 1
    const val MESSAGE_INTENT_EXTRA_KEY = "notification_message"
    const val LIKE_ACTION_TITLE = "Like"

    const val SENDING_NOTIFICATION_SUCCESS_MESSAGE = "The message has been successfully sent."
    const val SENDING_NOTIFICATION_ERROR_MESSAGE = "Something went wrong while sending a message."

    const val MESSAGE_TEXT_FIELD_PLACE_HOLDER = "Write a message"
    const val SEND_MESSAGE_BUTTON_TEXT = "Send"

    @SuppressLint("InlinedApi")
    const val POST_NOTIFICATIONS_PERMISSION = Manifest.permission.POST_NOTIFICATIONS

    const val OK = "OK"
    const val CANCEL = "Cancel"

    const val POST_NOTIFICATIONS_PERMISSION_TITLE = "Post notifications permission required"
    const val POST_NOTIFICATIONS_PERMISSION_TEXT = "To post notifications you must give the necessary permission"
}