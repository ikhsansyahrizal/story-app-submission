package com.ikhsan.storyapp.ui.domain

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<RegisterUserRes>>

}