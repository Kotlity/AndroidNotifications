package com.kotlity.notificationpractise.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.kotlity.notificationpractise.presentation.event.NotificationEvent
import com.kotlity.notificationpractise.presentation.screen.MessageNotificationScreen
import com.kotlity.notificationpractise.presentation.ui.theme.NotificationPractiseTheme
import com.kotlity.notificationpractise.presentation.viewmodel.NotificationViewModel
import com.kotlity.notificationpractise.utils.Constants.MESSAGE_INTENT_EXTRA_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val notificationViewModel by viewModels<NotificationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val notificationMessage = intent.getStringExtra(MESSAGE_INTENT_EXTRA_KEY)
        notificationMessage?.let {
            notificationViewModel.onEvent(NotificationEvent.OnUpdateNotificationMessage(it))
        }
        setContent {
            NotificationPractiseTheme {
                val messageState = notificationViewModel.notificationScreenState
                val messageToastFlow = notificationViewModel.messageToastFlow

                MessageNotificationScreen(
                    notificationScreenState = messageState,
                    messageToastFlow = messageToastFlow,
                    onEvent = notificationViewModel::onEvent
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val notificationMessage = intent.getStringExtra(MESSAGE_INTENT_EXTRA_KEY)
        notificationMessage?.let {
            notificationViewModel.onEvent(NotificationEvent.OnUpdateNotificationMessage(it))
        }
    }
}