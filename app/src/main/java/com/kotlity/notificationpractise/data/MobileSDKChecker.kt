package com.kotlity.notificationpractise.data

import android.os.Build
import com.kotlity.notificationpractise.domain.SDKChecker
import javax.inject.Inject

class MobileSDKChecker @Inject constructor(): SDKChecker {

    override fun isGreaterOrEqualThan(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
}