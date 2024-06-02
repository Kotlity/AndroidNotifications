package com.kotlity.notificationpractise.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.kotlity.notificationpractise.utils.Constants

@Composable
fun IdleMessageNotificationScreenState(
    message: String,
    notificationMessage: String? = null,
    onMessageChange: (String) -> Unit,
    onSendButtonClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        notificationMessage?.let {
            Text(text = it)
        }
        OutlinedTextField(
            value = message,
            onValueChange = onMessageChange,
            placeholder = {
                Text(text = Constants.MESSAGE_TEXT_FIELD_PLACE_HOLDER)
            }
        )
        OutlinedButton(onClick = onSendButtonClick) {
            Text(text = Constants.SEND_MESSAGE_BUTTON_TEXT)
        }
    }
}