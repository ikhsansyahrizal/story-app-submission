package com.ikhsan.storyapp.core.data.datasource.auth

import com.ikhsan.storyapp.base.BaseRemote
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.base.wrapper.RemoteResult
import com.ikhsan.storyapp.core.data.request.LoginReq
import com.ikhsan.storyapp.core.data.request.RegisterUserReq
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor() : AuthDataSource, BaseRemote() {

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
        return when (val remoteResult = getRemoteResult(apiCall = api.registerUser(request))) {
            is RemoteResult.Success -> ConsumeResultRemote.Success(data = remoteResult.data)
            is RemoteResult.Error -> {
                ConsumeResultRemote.Error(
                    code = remoteResult.code,
                    message = remoteResult.data.message
                )
            }
        }
    }

    override suspend fun doLogin(email: String, password: String): ConsumeResultRemote<LoginRes> {
        val request = LoginReq(
            email = email,
            password = password
        )
        return when (val remoteResult = getRemoteResult(apiCall = api.doLogin(request))) {
            is RemoteResult.Success -> {
                ConsumeResultRemote.Success(data = remoteResult.data)
            }
            is RemoteResult.Error -> {
                ConsumeResultRemote.Error(
                    code = remoteResult.code,
                    message = remoteResult.data.message
                )
            }
        }
    }
}