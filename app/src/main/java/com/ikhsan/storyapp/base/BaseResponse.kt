package com.ikhsan.storyapp.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T> (

    @field:SerializedName("responseCode")
    val code: String? = "",

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = ""
)