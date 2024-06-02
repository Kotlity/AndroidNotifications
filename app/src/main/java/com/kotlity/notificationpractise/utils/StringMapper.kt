package com.kotlity.notificationpractise.utils

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.kotlity.notificationpractise.utils.Constants.SENDING_NOTIFICATION_ERROR_MESSAGE
import com.kotlity.notificationpractise.utils.Constants.SENDING_NOTIFICATION_SUCCESS_MESSAGE

sealed interface UiText {
    data class DynamicString(val value: String): UiText
    class StringResource(
        @StringRes val resId: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asComposeString(): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> LocalContext.current.getString(resId, *args)
        }
    }

    fun asXMLString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}

fun NotificationSuccess.asUiText(): UiText {
    return when(this) {
        NotificationSuccess.SENDING_NOTIFICATION_SUCCESS -> UiText.DynamicString(value = SENDING_NOTIFICATION_SUCCESS_MESSAGE)
    }
}

fun NotificationError.asUiText(): UiText {
    return when(this) {
        NotificationError.SENDING_NOTIFICATION_ERROR -> UiText.DynamicString(value = SENDING_NOTIFICATION_ERROR_MESSAGE)
    }
}

fun Response.Success<NotificationSuccess, *>.asSuccessUiText(): UiText {
    return data.asUiText()
}

fun Response.Error<*, NotificationError>.asErrorUiText(): UiText {
    return error.asUiText()
}