package com.kotlity.notificationpractise.data

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.kotlity.notificationpractise.domain.NotifyRepository
import com.kotlity.notificationpractise.domain.PermissionChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotifyUserRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val permissionChecker: PermissionChecker
): NotifyRepository {

    override val notificationManagerCompat: NotificationManagerCompat
        get() = NotificationManagerCompat.from(context)

    override val areNotificationsEnabled: Boolean
        get() = notificationManagerCompat.areNotificationsEnabled()

    @SuppressLint("MissingPermission")
    override fun notify(notificationId: Int, notification: Notification) {
        if (permissionChecker.isPermissionGranted(Manifest.permission.POST_NOTIFICATIONS)) {
            notificationManagerCompat.notify(notificationId, notification)
        }
    }
}