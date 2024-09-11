package com.ikhsan.storyapp.base.wrapper

sealed class ConsumeResultDomain<out R> {
    data class Success<out T>(val data: T) : ConsumeResultDomain<T>()
    data class Error<out T>(val code: String? = "", val message: String, val data: T? = null, val status: String? = null) : ConsumeResultDomain<T>()
    data class ErrorAuth(val message: String) : ConsumeResultDomain<Nothing>()
}