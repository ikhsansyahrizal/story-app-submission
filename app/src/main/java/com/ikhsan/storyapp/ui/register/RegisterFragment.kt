package com.ikhsan.storyapp.ui.register

import com.ikhsan.storyapp.databinding.FragmentRegisterBinding
import com.ikhsan.storyeapp.base.BaseFragment


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    override fun initView() {
        super.initView()

        bind.edRegisterName.apply {
            setTextInputLayout(bind.textInputLayoutName)
            setMaxLength(20)
            maxLines = 1
        }

        bind.edRegisterEmail.apply {
            setTextInputLayout(bind.textInputLayoutEmail)
            setEmailFormat(true)
            setMaxLength(20)
            maxLines = 1
        }

        bind.edRegisterPassword.apply {
            setMinLength(8)
            setTextInputLayout(bind.textInputLayoutPassowrd)
            setMaxLength(20)
            maxLines = 1
        }

    }



}