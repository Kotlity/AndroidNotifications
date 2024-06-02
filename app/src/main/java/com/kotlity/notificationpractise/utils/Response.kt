package com.kotlity.notificationpractise.utils

sealed interface ApplicationSuccess

sealed interface ApplicationError

sealed interface Response<out D: ApplicationSuccess, out E: ApplicationError> {
    data class Success<out D: ApplicationSuccess, out E: ApplicationError>(val data: D): Response<D, E>
    data class Error<out D: ApplicationSuccess, out E: ApplicationError>(val error: E): Response<D, E>
    class Loading<out D: ApplicationSuccess, out E: ApplicationError>: Response<D, E>
}