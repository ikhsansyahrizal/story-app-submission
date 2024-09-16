package com.ikhsan.storyapp.ui.domain.story

import android.graphics.Bitmap
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.repository.story.StoryRepository
import com.ikhsan.storyapp.core.data.request.AddNewStoryReq
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.ListStory
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class StoryUseCaseImpl @Inject constructor(private val repo: StoryRepository) : StoryUseCase {
    override fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<List<ListStory>>> {
        return repo.getAllStories(page = page ,size = size, location = location)
    }

    override fun addNewStory(
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<ConsumeResultDomain<AddNewStoryRes>> {
        return repo.addNewStory(description = description, image = image)
    }
}