package com.ikhsan.storyapp.ui.starter

import androidx.lifecycle.ViewModel
import com.ikhsan.storyapp.ui.domain.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StartingViewModel @Inject constructor(private val useCase: AuthUseCase) : ViewModel() {

    fun isLogin(): Flow<Boolean> = useCase.isLogin()

}