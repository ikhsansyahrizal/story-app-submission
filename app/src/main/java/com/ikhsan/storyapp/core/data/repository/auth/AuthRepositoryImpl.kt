package com.ikhsan.storyapp.core.data.repository.auth

import com.ikhsan.storyapp.base.BaseRepository
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.datasource.auth.AuthDataSource
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remote: AuthDataSource): AuthRepository, BaseRepository() {
    override fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<RegisterUserRes>> {
        return flow {
            when(val result = remote.registerUser(username = username, email = email, password = password)) {
                is ConsumeResultRemote.Success -> emit(ConsumeResultDomain.Success(result.data))
                is ConsumeResultRemote.Error -> emit(ConsumeResultDomain.Error(message = result.message.orEmpty()))
                is ConsumeResultRemote.ErrorAuth -> emit(ConsumeResultDomain.ErrorAuth(result.message.orEmpty()))
            }
        }.catch {
            emit(ConsumeResultDomain.Error(message = it.localizedMessage.orEmpty()))
        }.flowOn(ioDispatcher)
    }

    override fun doLogin(email: String, password: String): Flow<ConsumeResultDomain<LoginRes>> {
        return flow {
            when(val result = remote.doLogin( email = email, password = password)) {
                is ConsumeResultRemote.Success -> emit(ConsumeResultDomain.Success(result.data))
                is ConsumeResultRemote.Error -> emit(ConsumeResultDomain.Error(message = result.message.orEmpty()))
                is ConsumeResultRemote.ErrorAuth -> emit(ConsumeResultDomain.ErrorAuth(result.message.orEmpty()))
            }
        }.catch {
            emit(ConsumeResultDomain.Error(message = it.localizedMessage.orEmpty()))
        }.flowOn(ioDispatcher)
    }
}