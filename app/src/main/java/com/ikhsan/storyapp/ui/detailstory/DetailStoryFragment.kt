package com.ikhsan.storyapp.ui.detailstory

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ikhsan.storyapp.base.helper.formatToReadableDate
import com.ikhsan.storyapp.base.helper.loadImageFrom
import com.ikhsan.storyapp.databinding.FragmentDetailStoryBinding
import com.ikhsan.storyeapp.base.BaseFragment


class DetailStoryFragment: BaseFragment<FragmentDetailStoryBinding>(FragmentDetailStoryBinding::inflate) {

    private val args by navArgs<DetailStoryFragmentArgs>()


    override fun initView() {
        bind.apply {
            ivDetailPhoto.loadImageFrom(args.data.photoUrl.orEmpty())
            tvDetailDate.text = args.data.createdAt?.formatToReadableDate()
            tvDetailName.text = args.data.name
            tvDetailDescription.text = args.data.description
        }
    }

    override fun initListener() {
        bind.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}