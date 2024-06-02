package com.kotlity.notificationpractise.presentation.composables

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationRationaleTextButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.textButtonColors(),
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        colors = colors,
        onClick = onClick
    ) {
        Text(text = text)
    }
}