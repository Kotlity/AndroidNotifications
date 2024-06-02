package com.kotlity.notificationpractise.utils

import android.content.Context
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState

suspend fun SnackbarHostState.showSnackbar(
    uiText: UiText,
    context: Context,
    actionLabel: String? = null,
    withDismissAction: Boolean = false,
    duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite
) {
    showSnackbar(
        message = uiText.asXMLString(context),
        withDismissAction = withDismissAction,
        duration = duration
    )
}