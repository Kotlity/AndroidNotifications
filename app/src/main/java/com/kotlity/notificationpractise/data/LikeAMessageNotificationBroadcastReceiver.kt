package com.kotlity.notificationpractise.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kotlity.notificationpractise.domain.MessageRepository
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_ID
import com.kotlity.notificationpractise.utils.NotificationError
import com.kotlity.notificationpractise.utils.NotificationSuccess
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LikeAMessageNotificationBroadcastReceiver: BroadcastReceiver() {

    @Inject
    lateinit var messageRepository: MessageRepository<NotificationSuccess, NotificationError>

    override fun onReceive(context: Context, intent: Intent?) {
        messageRepository.closeNotification(NOTIFICATION_ID)
    }
}