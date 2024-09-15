package com.ikhsan.storyapp.core.data.repository.story

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.ListStory
import kotlinx.coroutines.flow.Flow


interface StoryRepository {

    fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<List<ListStory>>>

}