package com.ikhsan.storyapp.base.wrapper

sealed class ConsumeResultRemote<out R> {
    data class Success<out T>(val data: T) : ConsumeResultRemote<T>()
    data class Error(val code: String? = "", val message: String? = "", val status: String? = "") : ConsumeResultRemote<Nothing>()
    data class ErrorAuth(val code: String? = "", val message: String? = "") : ConsumeResultRemote<Nothing>()

}