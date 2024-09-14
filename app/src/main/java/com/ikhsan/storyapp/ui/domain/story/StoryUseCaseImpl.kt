package com.ikhsan.storyapp.ui.domain.story

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.repository.story.StoryRepository
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryUseCaseImpl @Inject constructor(private val repo: StoryRepository) : StoryUseCase {
    override fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<GetAllStoriesRes>> {
        return repo.getAllStories(page = page ,size = size, location = location)
    }
}