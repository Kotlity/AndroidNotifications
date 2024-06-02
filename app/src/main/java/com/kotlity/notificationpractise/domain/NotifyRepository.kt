package com.kotlity.notificationpractise.domain

import android.app.Notification
import androidx.core.app.NotificationManagerCompat

interface NotifyRepository {

    val notificationManagerCompat: NotificationManagerCompat

    val areNotificationsEnabled: Boolean

    fun notify(notificationId: Int, notification: Notification)

}