package com.kotlity.notificationpractise.utils

import android.content.Intent

inline fun <reified T: Any> Intent.getArgumentFromIntent(
    key: String,
    defaultValue: T
): T? {
    return when(T::class) {
        Int::class -> getIntExtra(key, defaultValue as Int) as T
        Double::class -> getDoubleExtra(key, defaultValue as Double) as T
        Float::class -> getFloatExtra(key, defaultValue as Float) as T
        Long::class -> getLongExtra(key, defaultValue as Long) as T
        Boolean::class -> getBooleanExtra(key, defaultValue as Boolean) as T
        String::class -> getStringExtra(key) as T?
        else -> throw IllegalStateException("No such intent argument type")
    }
}