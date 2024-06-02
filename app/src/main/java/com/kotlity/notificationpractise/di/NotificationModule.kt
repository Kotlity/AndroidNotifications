package com.kotlity.notificationpractise.di

import com.kotlity.notificationpractise.data.MobilePermissionChecker
import com.kotlity.notificationpractise.data.MobileSDKChecker
import com.kotlity.notificationpractise.data.NotificationMessageRepository
import com.kotlity.notificationpractise.data.NotifyUserRepository
import com.kotlity.notificationpractise.domain.MessageRepository
import com.kotlity.notificationpractise.domain.NotifyRepository
import com.kotlity.notificationpractise.domain.PermissionChecker
import com.kotlity.notificationpractise.domain.SDKChecker
import com.kotlity.notificationpractise.utils.NotificationError
import com.kotlity.notificationpractise.utils.NotificationSuccess
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Singleton
    @Binds
    abstract fun bindNotificationRepository(
        notificationMessageRepository: NotificationMessageRepository
    ): MessageRepository<NotificationSuccess, NotificationError>

    @Singleton
    @Binds
    abstract fun bindNotifyUserRepository(
        notifyUserRepository: NotifyUserRepository
    ): NotifyRepository

    @Singleton
    @Binds
    abstract fun bindSDKChecker(
        mobileSDKChecker: MobileSDKChecker
    ): SDKChecker

    @Singleton
    @Binds
    abstract fun bindPermissionChecker(
        mobilePermissionChecker: MobilePermissionChecker
    ): PermissionChecker
}