package com.ikhsan.storyapp.core.data.response

import com.google.gson.annotations.SerializedName

data class AddNewStoryRes (

    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("message")
    val message: String? = null,
)