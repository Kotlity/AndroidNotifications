package com.kotlity.notificationpractise.di

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.kotlity.notificationpractise.domain.SDKChecker
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CHANNEL_DESCRIPTION
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.kotlity.notificationpractise.utils.Constants.NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication: Application() {

    @Inject
    lateinit var sdkChecker: SDKChecker

    private var notificationChannel: NotificationChannel? = null

    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    @SuppressLint("NewApi")
    private fun registerChannel() {
        notificationChannel = if (sdkChecker.isGreaterOrEqualThan(Build.VERSION_CODES.O)) {
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = NOTIFICATION_CHANNEL_DESCRIPTION
            }
        } else null
        if (notificationChannel != null) notificationManager.createNotificationChannel(notificationChannel!!)
    }

    override fun onCreate() {
        super.onCreate()
        registerChannel()
    }

}