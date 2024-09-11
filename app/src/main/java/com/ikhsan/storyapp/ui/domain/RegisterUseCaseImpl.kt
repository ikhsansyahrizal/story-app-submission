package com.ikhsan.storyapp.ui.domain

import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.repository.RegisterRepository
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCaseImpl @Inject constructor(private val repo: RegisterRepository) : RegisterUseCase {
    override fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<RegisterUserRes>> {
        return repo.registerUser(username, email, password)
    }
}