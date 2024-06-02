package com.kotlity.notificationpractise.presentation

import com.kotlity.notificationpractise.utils.UiText

sealed interface MessageToastInfo {
    data class Success(val message: UiText): MessageToastInfo
    data class Error(val error: UiText): MessageToastInfo
}