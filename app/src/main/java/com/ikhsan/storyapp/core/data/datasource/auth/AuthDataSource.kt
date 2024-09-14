package com.ikhsan.storyapp.core.data.datasource.auth

import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes

interface AuthDataSource {

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ConsumeResultRemote<RegisterUserRes>

    suspend fun doLogin(
        email: String,
        password: String
    ): ConsumeResultRemote<LoginRes>
}