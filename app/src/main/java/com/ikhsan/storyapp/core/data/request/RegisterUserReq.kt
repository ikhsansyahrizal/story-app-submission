package com.ikhsan.storyapp.core.data.request

import com.google.gson.annotations.SerializedName

data class RegisterUserReq(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String
)
