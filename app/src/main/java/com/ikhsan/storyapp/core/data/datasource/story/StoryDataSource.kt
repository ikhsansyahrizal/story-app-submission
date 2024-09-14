package com.ikhsan.storyapp.core.data.datasource.story

import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes

interface StoryDataSource {

    suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): ConsumeResultRemote<GetAllStoriesRes>

}