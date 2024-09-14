package com.ikhsan.storyapp.core.data.response

import com.google.gson.annotations.SerializedName

data class GetAllStoriesRes(

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("loginResult")
    val listStory: ListStory? = null,

)

data class ListStory(

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = 0.0,

    @field:SerializedName("lon")
    val lon: Double? = 0.0,

    )