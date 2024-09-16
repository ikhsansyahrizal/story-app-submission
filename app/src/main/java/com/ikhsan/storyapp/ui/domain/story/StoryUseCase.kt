package com.ikhsan.storyapp.ui.domain.story

import android.graphics.Bitmap
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.request.AddNewStoryReq
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.ListStory
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface StoryUseCase {

    fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<List<ListStory>>>

    fun addNewStory(description: RequestBody, image: MultipartBody.Part): Flow<ConsumeResultDomain<AddNewStoryRes>>
}