package com.kotlity.notificationpractise.domain

import com.kotlity.notificationpractise.utils.ApplicationError
import com.kotlity.notificationpractise.utils.ApplicationSuccess
import com.kotlity.notificationpractise.utils.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository<D: ApplicationSuccess, E: ApplicationError> {

    fun sendMessage(message: String): Flow<Response<D, E>>

    fun closeNotification(notificationId: Int)
}