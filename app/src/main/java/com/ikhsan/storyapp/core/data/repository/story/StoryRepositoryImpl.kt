package com.ikhsan.storyapp.core.data.repository.story

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ikhsan.storyapp.base.BaseRepository
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.datasource.story.StoryDataSource
import com.ikhsan.storyapp.core.data.mediator.StoryRemoteMediator
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.core.network.ApiInterface
import com.ikhsan.storyapp.core.room.StoryDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val story: StoryDataSource,
    private val database: StoryDatabase,
    private val apiInterface: ApiInterface
    ) : StoryRepository, BaseRepository() {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = StoryRemoteMediator(database, apiInterface, 0),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStoriesWithLocation(): LiveData<PagingData<ListStory>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            remoteMediator = StoryRemoteMediator(database, apiInterface, 1),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    override fun addNewStory(description: RequestBody, image: MultipartBody.Part): Flow<ConsumeResultDomain<AddNewStoryRes>> {
        return flow {
            when (val result =
                story.addNewStory(description = description, image = image)) {
                is ConsumeResultRemote.Success -> { emit(ConsumeResultDomain.Success(result.data))                }
                is ConsumeResultRemote.Error -> emit(ConsumeResultDomain.Error(message = result.message.orEmpty()))
                is ConsumeResultRemote.ErrorAuth -> emit(ConsumeResultDomain.ErrorAuth(result.message.orEmpty()))
            }
        }.catch {
            emit(ConsumeResultDomain.Error(message = it.localizedMessage.orEmpty()))
        }.flowOn(ioDispatcher)
    }
}