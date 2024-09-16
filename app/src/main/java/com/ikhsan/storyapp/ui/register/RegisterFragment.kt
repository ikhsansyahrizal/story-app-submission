package com.ikhsan.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import androidx.fragment.app.viewModels
import com.ikhsan.storyapp.R
import com.ikhsan.storyapp.base.helper.getTexts
import com.ikhsan.storyapp.base.helper.observe
import com.ikhsan.storyapp.databinding.FragmentRegisterBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun initView() {
        playAnimation()
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


    override fun initListener() {
        bind.btnRegister.setOnClickListener {
            viewModel.registerUser(
                bind.edRegisterName.getTexts(),
                bind.edRegisterEmail.getTexts(),
                bind.edRegisterPassword.getTexts()
            )
        }
    }

    override fun initObserver() {
        observe(viewModel.register) { data ->
            data?.data?.let {
                displayMessage(title = it.message.orEmpty(), icon = R.drawable.ic_check,
                    ok = { gooTo(RegisterFragmentDirections.toLogin()) })
            }
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }
    }

    private fun playAnimation() {
        val nameLayout = ObjectAnimator.ofFloat(bind.textInputLayoutName, View.ALPHA, 1f).setDuration(500)
        val nameEt = ObjectAnimator.ofFloat(bind.edRegisterName, View.ALPHA, 1f).setDuration(500)

        val emailLayout = ObjectAnimator.ofFloat(bind.textInputLayoutEmail, View.ALPHA, 1f).setDuration(500)
        val emailEt = ObjectAnimator.ofFloat(bind.edRegisterEmail, View.ALPHA, 1f).setDuration(500)

        val passLayout = ObjectAnimator.ofFloat(bind.textInputLayoutPassowrd, View.ALPHA, 1f).setDuration(500)
        val passEt = ObjectAnimator.ofFloat(bind.edRegisterPassword, View.ALPHA, 1f).setDuration(500)

        val loginBtn = ObjectAnimator.ofFloat(bind.btnRegister, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(nameLayout, nameEt, emailLayout, emailEt, passLayout, passEt, loginBtn)
            start()
        }
    }

}