package com.ikhsan.storyapp.ui.home

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikhsan.storyapp.base.helper.EndlessRecyclerViewScrollListener
import com.ikhsan.storyapp.base.helper.WrapContentLinearLayoutManager
import com.ikhsan.storyapp.base.helper.initRecycleView
import com.ikhsan.storyapp.base.helper.observe
import com.ikhsan.storyapp.base.helper.onSwipeListener
import com.ikhsan.storyapp.databinding.FragmentHomeBinding
import com.ikhsan.storyapp.ui.home.adapter.StoryAdapter
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private val adapter by lazy { StoryAdapter(this) }
    private val mLinearLayoutManager: LinearLayoutManager by lazy {
        WrapContentLinearLayoutManager(requireContext())
    }
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener


    override fun initView() {
        setAdapter()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) { override fun handleOnBackPressed() {} }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun initObserver() {
        observe(viewModel.stories) { data ->
            data?.data.let {
                if (it != null) {
                    adapter.addAll(it)
                }
            }
            data?.specialMessage?.let {
                displayInfoMessage(it)
            }
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }
    }

    override fun initListener() {
        initScrollListener()

        bind.swipe.onSwipeListener {
            scrollListener.resetState()
            viewModel.getAllStories(page = 1, size = 10, location = null)
        }

        bind.ivLogOut.setOnClickListener {
            viewModel.doLogOut()
            gooTo(HomeFragmentDirections.toLogin())
        }
    }

    private fun setAdapter() {
        adapter.clear()
        viewModel.getAllStories(page = 1, size = 10, location = null)
        bind.rvStory.initRecycleView(
            adapterRV = adapter,
            customLinearLayoutManager = mLinearLayoutManager
        )
    }

    private fun initScrollListener() {
        scrollListener = object : EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.getAllStories(page = page, size = 10, location = null)
            }
        }
        bind.rvStory.addOnScrollListener(scrollListener)
    }
}
