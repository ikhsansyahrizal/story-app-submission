package com.ikhsan.storyapp.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikhsan.storyapp.MainActivity
import com.ikhsan.storyapp.R
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

        playAnimation()
    }

    override fun initObserver() {
        observe(viewModel.stories) { data ->
            data?.data.let {
                if (it != null) {
                    adapter.addAll(it)
                }
            }
            data?.specialMessage?.forceLogout()
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }
    }

    override fun initListener() {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as? MainActivity)?.handleBackPress()
            }
        })
        initScrollListener()

        bind.buttonAdd.setOnClickListener {
            gooTo(HomeFragmentDirections.toAddStory())
        }

        bind.swipe.onSwipeListener {
            scrollListener.resetState()
            viewModel.getAllStories(page = 1, size = 10, location = null)
        }

        bind.actionSetting.setOnClickListener {
            alertDialogSetting()
        }

        adapter.onTapItem = {
            gooTo(HomeFragmentDirections.toDetail(it))
        }
    }

    private fun alertDialogSetting() {
        val settingList = resources.getStringArray(R.array.list_setting)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(resources.getString(R.string.menu_setting))
        builder.setItems(settingList) { _, which ->
            when (which) {
                0 -> {
                    startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                }
                1 -> {
                    viewModel.doLogOut()
                    gooTo(HomeFragmentDirections.toLogin())
                }
            }
        }
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.btn_background)
        alertDialog.show()
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

    private fun playAnimation() {
        val titleTv = ObjectAnimator.ofFloat(bind.tvTitle, View.ALPHA, 1f).setDuration(500)
        val logoutBtn = ObjectAnimator.ofFloat(bind.actionSetting, View.ALPHA, 1f).setDuration(500)

        val rv = ObjectAnimator.ofFloat(bind.swipe, View.ALPHA, 1f).setDuration(500)

        val addBtn = ObjectAnimator.ofFloat(bind.buttonAdd, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(titleTv, logoutBtn)
            playSequentially(rv, addBtn)
            start()
        }
    }
}
