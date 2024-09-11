package com.ikhsan.storyapp.core.data.datasource

import android.util.Log
import com.ikhsan.storyapp.base.BaseRemote
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.base.wrapper.RemoteResult
import com.ikhsan.storyapp.core.data.request.RegisterUserReq
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import javax.inject.Inject

class RegisterDataSourceImpl @Inject constructor(): RegisterDataSource, BaseRemote() {

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ConsumeResultRemote<RegisterUserRes> {
        val request = RegisterUserReq(
            name = username,
            email = email,
            password = password
        )
        return when(val remoteResult = getRemoteResult(apiCall = api.registerUser(request))) {
            is RemoteResult.Success -> ConsumeResultRemote.Success(data = remoteResult.data)
            is RemoteResult.Error -> {
                Log.d("datasource", "${remoteResult}")
                Log.d("datasource 2", "${remoteResult.code}")
                ConsumeResultRemote.Error(code = remoteResult.code, message = remoteResult.data.message)
            }
        }
    }
}