package com.ikhsan.storyapp.ui.domain.auth

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import kotlinx.coroutines.flow.Flow

interface AuthUseCase {

    fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<RegisterUserRes>>

    fun doLogin(
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<LoginRes>>

    fun isLogin(): Boolean

    fun doLogOut()

}