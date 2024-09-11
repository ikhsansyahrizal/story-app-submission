package com.ikhsan.storyapp.base.helper.remote

import com.ikhsan.storyapp.base.BaseResponse
import com.ikhsan.storyapp.base.helper.fromJson
import com.ikhsan.storyapp.base.wrapper.RemoteResult
import com.ikhsan.storyapp.core.network.NoConnectivityException
import retrofit2.Response

class RemoteHelperImpl : RemoteHelper {

    override suspend fun <T> getRemoteResult(apiCall: Response<T>): RemoteResult<T> {
        return try {
            if (apiCall.isSuccessful) {
                val body = apiCall.body()
                if (body != null) RemoteResult.Success(body) else RemoteResult.Error(apiCall.code().toString(), BaseResponse(message = "body is null"))
            } else {
                val data = apiCall.errorBody()?.string().fromJson<BaseResponse<String>>() ?: BaseResponse(message = "Gagal mendapatkan data, coba beberapa saat lagi.[${apiCall.code()}]")
                RemoteResult.Error(code = apiCall.code().toString(), data = data)
            }
        } catch (e: Exception) {
            if (e is NoConnectivityException) RemoteResult.Error("999", BaseResponse(message = e.localizedMessage))
            else RemoteResult.Error(apiCall.code().toString(), BaseResponse(message = e.localizedMessage))
        }
    }
}