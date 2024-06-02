package com.kotlity.notificationpractise.domain

interface PermissionChecker {

    fun isPermissionGranted(permission: String): Boolean
}