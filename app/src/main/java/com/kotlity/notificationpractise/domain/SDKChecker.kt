package com.kotlity.notificationpractise.domain

interface SDKChecker {

    fun isGreaterOrEqualThan(version: Int): Boolean
}