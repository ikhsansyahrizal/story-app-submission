package com.ikhsan.storyapp.core.data.datasource.story

import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.request.AddNewStoryReq
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface StoryDataSource {

    suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): ConsumeResultRemote<GetAllStoriesRes>

    suspend fun addNewStory(
        description: RequestBody,
        image: MultipartBody.Part
    ): ConsumeResultRemote<AddNewStoryRes>

}