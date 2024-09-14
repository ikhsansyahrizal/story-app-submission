package com.ikhsan.storyapp.ui.starter

import androidx.fragment.app.viewModels
import com.ikhsan.storyapp.databinding.FragmentStartingBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartingFragment : BaseFragment<FragmentStartingBinding>(FragmentStartingBinding::inflate) {

    private val viewModel: StartingViewModel by viewModels()

    override fun initView() {
        if (viewModel.isLogin()) gooTo(StartingFragmentDirections.toHome()) else gooTo(StartingFragmentDirections.toLogin())
    }

}