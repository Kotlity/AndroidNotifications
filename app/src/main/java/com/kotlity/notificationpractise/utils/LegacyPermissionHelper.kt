package com.kotlity.notificationpractise.utils

import android.app.Activity
import androidx.core.app.ActivityCompat

fun onPermissionDenied(
    activity: Activity,
    permission: String,
    onShowPermissionRationale: () -> Unit,
    onRequestPermissionAgain: () -> Unit
) {
    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) onShowPermissionRationale()
    else onRequestPermissionAgain()
}

fun requestPermission(activity: Activity, permission: String) = ActivityCompat.requestPermissions(activity, arrayOf(permission), 0)