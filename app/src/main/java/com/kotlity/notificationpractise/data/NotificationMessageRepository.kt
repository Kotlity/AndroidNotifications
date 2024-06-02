package com.kotlity.notificationpractise.data

import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import com.kotlity.notificationpractise.R
import com.kotlity.notificationpractise.domain.MessageRepository
import com.kotlity.notificationpractise.domain.NotifyRepository
import com.kotlity.notificationpractise.domain.SDKChecker
import com.kotlity.notificationpractise.presentation.MainActivity
import com.kotlity.notificationpractise.utils.Constants.LIKE_ACTION_TITLE
import com.kotlity.notificationpractise.utils.Constants.LIKE_A_MESSAGE_BROADCAST_PENDING_INTENT_REQUEST_CODE
import com.kotlity.notificationpractise.utils.Constants.MESSAGE_ACTIVITY_PENDING_INTENT_REQUEST_CODE
import com.kotlity.notificationpractise.utils.Constants.MESSAGE_INTENT_EXTRA_KEY
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CONTENT_TEXT_CHARACTER_LIMIT
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CONTENT_TITLE
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_ID
import com.kotlity.notificationpractise.utils.NotificationError
import com.kotlity.notificationpractise.utils.NotificationSuccess
import com.kotlity.notificationpractise.utils.Response
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationMessageRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val notifyRepository: NotifyRepository,
    private val sdkChecker: SDKChecker
): MessageRepository<NotificationSuccess, NotificationError> {

    override fun sendMessage(message: String): Flow<Response<NotificationSuccess, NotificationError>> {
        return flow {
            emit(Response.Loading())
            val notification = createNotification(message)
            if (notification != null) notifyRepository.notify(NOTIFICATION_ID, notification)
            else emit(Response.Error(error = NotificationError.SENDING_NOTIFICATION_ERROR))
            if (notifyRepository.areNotificationsEnabled) emit(Response.Success(data = NotificationSuccess.SENDING_NOTIFICATION_SUCCESS))
            else emit(Response.Error(error = NotificationError.SENDING_NOTIFICATION_ERROR))
        }
    }

    override fun closeNotification(notificationId: Int) {
        notifyRepository.notificationManagerCompat.cancel(notificationId)
    }

    @SuppressLint("NewApi")
    private fun createNotification(message: String): Notification? {
        val messageActivityIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(MESSAGE_INTENT_EXTRA_KEY, message)
        }
        val messageActivityPendingIntent = PendingIntent.getActivity(
            context,
            MESSAGE_ACTIVITY_PENDING_INTENT_REQUEST_CODE,
            messageActivityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val likeMessageIntent = Intent(context, LikeAMessageNotificationBroadcastReceiver::class.java)
        val likeMessagePendingIntent = PendingIntent.getBroadcast(
            context,
            LIKE_A_MESSAGE_BROADCAST_PENDING_INTENT_REQUEST_CODE,
            likeMessageIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val messageTextStyle = Notification.BigTextStyle().bigText(message)
        val likeAMessageAction = Notification.Action.Builder(
            Icon.createWithResource(context, R.drawable.like_icon),
            LIKE_ACTION_TITLE,
            likeMessagePendingIntent
        ).build()
        return if (sdkChecker.isGreaterOrEqualThan(Build.VERSION_CODES.O)) {
            Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(NOTIFICATION_CONTENT_TITLE)
                .setContentText(message.take(NOTIFICATION_CONTENT_TEXT_CHARACTER_LIMIT))
                .setStyle(messageTextStyle)
                .setContentIntent(messageActivityPendingIntent)
                .addAction(likeAMessageAction)
                .setAutoCancel(true)
                .setOngoing(true)
                .build()
        } else null
    }
}