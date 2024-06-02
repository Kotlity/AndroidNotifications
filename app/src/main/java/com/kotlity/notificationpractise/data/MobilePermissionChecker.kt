package com.kotlity.notificationpractise.data

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.kotlity.notificationpractise.domain.PermissionChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MobilePermissionChecker @Inject constructor (@ApplicationContext private val context: Context): PermissionChecker {

    override fun isPermissionGranted(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}