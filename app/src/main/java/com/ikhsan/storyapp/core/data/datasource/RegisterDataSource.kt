package com.ikhsan.storyapp.core.data.datasource

import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.response.RegisterUserRes

interface RegisterDataSource {

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ConsumeResultRemote<RegisterUserRes>
}