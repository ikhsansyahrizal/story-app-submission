package com.ikhsan.storyapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhsan.storyapp.base.UIConstant
import com.ikhsan.storyapp.base.UIStateData
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.LoginRes
import com.ikhsan.storyapp.ui.domain.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val useCase: AuthUseCase): ViewModel() {
    
    private var _login = MutableLiveData<UIStateData<LoginRes>>()
    val login: LiveData<UIStateData<LoginRes>> = _login
    
    fun doLogin(email: String, password: String) = viewModelScope.launch {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            useCase.doLogin(email, password)
                .onStart { _login.value = UIStateData(loading = true) }
                .collect { data ->
                    when (data) {
                        is ConsumeResultDomain.Success -> _login.value =
                            UIStateData(data = data.data, loading = false)

                        is ConsumeResultDomain.Error -> _login.value =
                            UIStateData(message = data.message, loading = false)

                        else -> _login.value = UIStateData(message = UIConstant.GENERAL_ERROR, loading = false)
                    }
                }
        } else _login.value = UIStateData(message = "field can't be empty", loading = false)
    }
    
}