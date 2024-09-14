package com.ikhsan.storyapp.core.data.repository.story

import com.ikhsan.storyapp.base.BaseRepository
import com.ikhsan.storyapp.base.helper.local.PreferenceDataStoreHelper
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.datasource.story.StoryDataSource
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val story: StoryDataSource,
    private val prefDataStore: PreferenceDataStoreHelper
) : StoryRepository, BaseRepository() {
    override fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): Flow<ConsumeResultDomain<GetAllStoriesRes>> {
        return flow {
            when (val result =
                story.getAllStories(page = page, size = size,  location = location)) {
                is ConsumeResultRemote.Success -> emit(ConsumeResultDomain.Success(result.data))
                is ConsumeResultRemote.Error -> emit(ConsumeResultDomain.Error(message = result.message.orEmpty()))
                is ConsumeResultRemote.ErrorAuth -> emit(ConsumeResultDomain.ErrorAuth(result.message.orEmpty()))
            }
        }.catch {
            emit(ConsumeResultDomain.Error(message = it.localizedMessage.orEmpty()))
        }.flowOn(ioDispatcher)
    }

}