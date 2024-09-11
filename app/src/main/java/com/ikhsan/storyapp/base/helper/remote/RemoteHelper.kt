package com.ikhsan.storyapp.base.helper.remote

import com.ikhsan.storyapp.base.wrapper.RemoteResult
import retrofit2.Response

interface RemoteHelper {
    suspend fun <T> getRemoteResult(apiCall: Response<T>): RemoteResult<T>
}