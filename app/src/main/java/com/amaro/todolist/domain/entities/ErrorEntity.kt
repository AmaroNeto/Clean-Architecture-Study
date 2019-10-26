package com.amaro.todolist.domain.entities

sealed class ErrorEntity {

    abstract val originalException: Throwable

    data class NetWork(override val originalException: Throwable): ErrorEntity()

    data class NotFound(override val originalException: Throwable): ErrorEntity()

    data class ServiceUnavailable(override val originalException: Throwable): ErrorEntity()

    data class AccessDenied(override val originalException: Throwable) : ErrorEntity()

    data class Unknown(override val originalException: Throwable) : ErrorEntity()

}