package com.ikhsan.storyapp.ui.login

import androidx.fragment.app.viewModels
import com.ikhsan.storyapp.base.helper.getTexts
import com.ikhsan.storyapp.base.helper.observe
import com.ikhsan.storyapp.databinding.FragmentLoginBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {

        bind.edLoginEmail.apply {
            setTextInputLayout(bind.textInputLayoutUsername)
            setMaxLength(20)
            maxLines = 1
        }

        bind.edLoginPassword.apply {
            setTextInputLayout(bind.textInputLayoutPassword)
            setMaxLength(20)
            setMinLength(8)
            maxLines = 1
        }

    }

    override fun initListener() {

        bind.tvRegister.setOnClickListener {
            gooTo(LoginFragmentDirections.toRegister())
        }

        bind.btnLogin.setOnClickListener {
            viewModel.doLogin(bind.edLoginEmail.getTexts(), bind.edLoginPassword.getTexts())
        }
    }

    override fun initObserver() {

        observe(viewModel.login) { data ->
            data?.data?.let { gooTo(LoginFragmentDirections.toHome()) }
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }

    }


}