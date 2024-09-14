package com.ikhsan.storyapp.core.data.repository.auth

import android.util.Log
import com.ikhsan.storyapp.base.BaseRepository
import com.ikhsan.storyapp.base.helper.local.PreferenceDataStoreHelper
import com.ikhsan.storyapp.base.helper.local.PreferenceDataStoreHelperImpl.Companion.USER_SESSION
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.base.wrapper.ConsumeResultRemote
import com.ikhsan.storyapp.core.data.datasource.auth.AuthDataSource
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthDataSource,
    private val prefDataStore: PreferenceDataStoreHelper
) : AuthRepository, BaseRepository() {
    override fun registerUser(
        username: String,
        email: String,
        password: String
    ): Flow<ConsumeResultDomain<RegisterUserRes>> {
        return flow {
            when (val result =
                remote.registerUser(username = username, email = email, password = password)) {
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
            when (val result = remote.doLogin(email = email, password = password)) {
                is ConsumeResultRemote.Success -> {
                    prefDataStore.savePreference(
                        USER_SESSION,
                        result.data.loginResult?.token.orEmpty()
                    )
                    emit(ConsumeResultDomain.Success(result.data))
                }

                is ConsumeResultRemote.Error -> emit(ConsumeResultDomain.Error(message = result.message.orEmpty()))
                is ConsumeResultRemote.ErrorAuth -> emit(ConsumeResultDomain.ErrorAuth(result.message.orEmpty()))
            }
        }.catch {
            emit(ConsumeResultDomain.Error(message = it.localizedMessage.orEmpty()))
        }.flowOn(ioDispatcher)
    }

    override fun isLogin(): Flow<Boolean> =
        prefDataStore.getPreference(USER_SESSION, "").map { token ->
            Log.d("repo", token)
            token.isNotEmpty()
        }
}