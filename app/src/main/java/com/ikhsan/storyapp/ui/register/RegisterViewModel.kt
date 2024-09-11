package com.ikhsan.storyapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhsan.storyapp.base.UIConstant
import com.ikhsan.storyapp.base.UIStateData
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.response.RegisterUserRes
import com.ikhsan.storyapp.ui.domain.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val useCase: RegisterUseCase): ViewModel() {

    private var _register = MutableLiveData<UIStateData<RegisterUserRes>>()
    val register: LiveData<UIStateData<RegisterUserRes>> = _register

    fun registerUser(username: String, email: String, password: String) = viewModelScope.launch {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            useCase.registerUser(username, email, password)
                .onStart { _register.value = UIStateData(loading = true) }
                .collect { data ->
                    when (data) {
                        is ConsumeResultDomain.Success -> _register.value =
                            UIStateData(data = data.data, loading = false)

                        is ConsumeResultDomain.Error -> _register.value =
                            UIStateData(message = data.message, loading = false)

                        else -> _register.value = UIStateData(message = UIConstant.GENERAL_ERROR, loading = false)
                    }
                }
        } else _register.value = UIStateData(message = "field can't be empty", loading = false)

    }





}