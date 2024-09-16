package com.ikhsan.storyapp.core.data.request

import com.google.gson.annotations.SerializedName
import okhttp3.RequestBody

data class AddNewStoryReq(

    @field:SerializedName("description")
    val description: RequestBody,

    @field:SerializedName("lat")
    val lat: RequestBody?,

    @field:SerializedName("lon")
    val lon: RequestBody?
)
