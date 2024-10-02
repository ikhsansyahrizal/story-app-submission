package com.ikhsan.storyapp.ui.home

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ikhsan.storyapp.MainActivity
import com.ikhsan.storyapp.R
import com.ikhsan.storyapp.base.helper.onSwipeListener
import com.ikhsan.storyapp.databinding.FragmentHomeBinding
import com.ikhsan.storyapp.ui.home.adapter.LoadingStateAdapter
import com.ikhsan.storyapp.ui.home.adapter.StoryPagingAdapter
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: StoryPagingAdapter

    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            navigateToMapFragment()
        } else {
            Toast.makeText(
                requireContext(),
                "Location permission is required to view the map",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun initView() {
        setupAdapter()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {}
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        playAnimation()
    }

    override fun initObserver() {
        viewModel.stories.observe(viewLifecycleOwner) { pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }
    }

    override fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    (activity as? MainActivity)?.handleBackPress()
                }
            })

        bind.buttonAdd.setOnClickListener {
            gooTo(HomeFragmentDirections.toAddStory())
        }

        bind.swipe.onSwipeListener {
            viewModel.refresh()
        }

        bind.imgMap.setOnClickListener {
            checkLocationPermission()
        }

        bind.actionSetting.setOnClickListener {
            alertDialogSetting()
        }

        adapter.onTapItem = {
            gooTo(HomeFragmentDirections.toDetail(it))
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.recyclerViewState = bind.rvStory.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.recyclerViewState?.let {
            bind.rvStory.layoutManager?.onRestoreInstanceState(it)
        }
    }
    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                navigateToMapFragment()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                Toast.makeText(
                    requireContext(),
                    "Location permission is needed to open the map",
                    Toast.LENGTH_SHORT
                ).show()
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

            else -> {
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun navigateToMapFragment() {
        gooTo(HomeFragmentDirections.toMap())
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

    private fun playAnimation() {
        val titleTv = ObjectAnimator.ofFloat(bind.tvTitle, View.ALPHA, 1f).setDuration(500)
        val logoutBtn = ObjectAnimator.ofFloat(bind.actionSetting, View.ALPHA, 1f).setDuration(500)
        val mapBtn = ObjectAnimator.ofFloat(bind.imgMap, View.ALPHA, 1f).setDuration(500)
        val rv = ObjectAnimator.ofFloat(bind.swipe, View.ALPHA, 1f).setDuration(500)
        val addBtn = ObjectAnimator.ofFloat(bind.buttonAdd, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(titleTv, logoutBtn, mapBtn)
            playSequentially(rv, addBtn)
            start()
        }
    }

    private fun setupAdapter() {
        bind.rvStory.layoutManager = LinearLayoutManager(requireContext())
        adapter = StoryPagingAdapter()
        bind.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
    }
}
