package com.ikhsan.storyapp.ui.starter

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ikhsan.storyapp.databinding.FragmentStartingBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartingFragment : BaseFragment<FragmentStartingBinding>(FragmentStartingBinding::inflate) {

    private val viewModel: StartingViewModel by viewModels()

    override fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLogin().collect { isLoggedIn ->
                if (isLoggedIn) {
                    gooTo(StartingFragmentDirections.toHome())
                } else {
                    gooTo(StartingFragmentDirections.toLogin())
                }
            }
        }    }

}