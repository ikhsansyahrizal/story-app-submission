package com.ikhsan.storyapp.core.data.request

import com.google.gson.annotations.SerializedName

data class LoginReq(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
