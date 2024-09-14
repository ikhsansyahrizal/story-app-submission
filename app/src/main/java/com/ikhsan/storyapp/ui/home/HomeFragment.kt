package com.ikhsan.storyapp.ui.home

import android.util.Log
import androidx.fragment.app.viewModels
import com.ikhsan.storyapp.base.helper.observe
import com.ikhsan.storyapp.databinding.FragmentHomeBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()


    override fun initView() {
        viewModel.getAllStories(null, null, null)
    }

    override fun initObserver() {
        observe(viewModel.stories) { data ->
            data?.data.let {
                Log.d("tess", "${data}")
            }
            data?.specialMessage?.let {
//                displayInfoMessage(it)
            }
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }
    }
}
