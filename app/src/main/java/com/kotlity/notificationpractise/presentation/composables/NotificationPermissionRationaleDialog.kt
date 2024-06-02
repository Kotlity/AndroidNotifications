package com.kotlity.notificationpractise.presentation.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kotlity.notificationpractise.utils.Constants.CANCEL
import com.kotlity.notificationpractise.utils.Constants.OK
import com.kotlity.notificationpractise.utils.Constants.POST_NOTIFICATIONS_PERMISSION_TEXT
import com.kotlity.notificationpractise.utils.Constants.POST_NOTIFICATIONS_PERMISSION_TITLE

@Composable
fun NotificationPermissionRationaleDialog(
    onDismiss: () -> Unit,
    confirmButtonContainerColor: Color = Color.Green,
    dismissButtonContainerColor: Color = Color.Red,
    confirmButtonText: String = OK,
    dismissButtonText: String = CANCEL,
    titleText: String = POST_NOTIFICATIONS_PERMISSION_TITLE,
    text: String = POST_NOTIFICATIONS_PERMISSION_TEXT,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            NotificationRationaleTextButton(
                colors = ButtonDefaults.textButtonColors().copy(containerColor = confirmButtonContainerColor),
                text = confirmButtonText,
                onClick = onConfirmButtonClick
            )
        },
        dismissButton = {
            NotificationRationaleTextButton(
                colors = ButtonDefaults.textButtonColors().copy(containerColor = dismissButtonContainerColor),
                text = dismissButtonText,
                onClick = onDismiss
            )
        },
        title = {
            Text(text = titleText)
        },
        text = {
            Text(text = text)
        }
    )
}