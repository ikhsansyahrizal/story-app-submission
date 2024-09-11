package com.ikhsan.storyapp.base.wrapper

import com.ikhsan.storyapp.base.BaseResponse


sealed class RemoteResult<out R> {
    data class Success<out T>(val data: T) : RemoteResult<T>()
    data class Error(
        val code: String,
        val data: BaseResponse<String>,
        val cause: Exception? = null,
        val error: String? = null,
        ) : RemoteResult<Nothing>()
}