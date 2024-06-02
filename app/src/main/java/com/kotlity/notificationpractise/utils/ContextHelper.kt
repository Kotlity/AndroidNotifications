package com.kotlity.notificationpractise.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Context.getActivity(): ComponentActivity? {
    return when(this) {
        is ComponentActivity -> return this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
}