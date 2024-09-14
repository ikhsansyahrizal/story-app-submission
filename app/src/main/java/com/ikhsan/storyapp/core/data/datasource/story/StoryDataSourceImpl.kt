package com.ikhsan.storyapp.core.data.datasource.story

import com.ikhsan.storyapp.base.BaseRemote
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.base.wrapper.RemoteResult
import com.ikhsan.storyapp.core.data.response.GetAllStoriesRes
import javax.inject.Inject

class StoryDataSourceImpl @Inject constructor() : StoryDataSource, BaseRemote() {

    override suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): ConsumeResultRemote<GetAllStoriesRes> {
        return when (val remoteResult = getRemoteResult(
            apiCall = api.getAllStories(
                page = page,
                size = size,
                location = location
            )
        )) {
            is RemoteResult.Success -> ConsumeResultRemote.Success(data = remoteResult.data)

            is RemoteResult.Error -> ConsumeResultRemote.Error(
                code = remoteResult.code,
                message = remoteResult.data.message
            )
        }
    }

}