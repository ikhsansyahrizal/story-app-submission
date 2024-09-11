package com.ikhsan.storyapp.ui.login

import com.ikhsan.storyapp.databinding.FragmentLoginBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    override fun initView() {
        super.initView()

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
        super.initListener()

        bind.tvRegister.setOnClickListener {
            gooTo(LoginFragmentDirections.toRegister())
        }
    }
}