package com.ikhsan.storyapp.core.data.repository.story

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.request.AddNewStoryReq
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.ListStory
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


interface StoryRepository {

    fun getAllStories(): LiveData<PagingData<ListStory>>

    fun getAllStoriesWithLocation(): LiveData<PagingData<ListStory>>

    fun addNewStory(
        description: RequestBody,
        image: MultipartBody.Part
    ): Flow<ConsumeResultDomain<AddNewStoryRes>>
}