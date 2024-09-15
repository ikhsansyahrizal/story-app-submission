package com.ikhsan.storyapp.ui.domain.story

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.ListStory
import kotlinx.coroutines.flow.Flow

interface StoryUseCase {

    fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<List<ListStory>>>

}