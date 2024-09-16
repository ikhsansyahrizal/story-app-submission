package com.ikhsan.storyapp.ui.detailstory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
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
        playAnimation()
    }

    override fun initListener() {
        bind.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun playAnimation() {
        val titleTv = ObjectAnimator.ofFloat(bind.tvTitle, View.ALPHA, 1f).setDuration(500)
        val logoutBtn = ObjectAnimator.ofFloat(bind.btnBack, View.ALPHA, 1f).setDuration(500)

        val photo = ObjectAnimator.ofFloat(bind.ivDetailPhoto, View.ALPHA, 1f).setDuration(500)

        val dateTv = ObjectAnimator.ofFloat(bind.tvDetailDate, View.ALPHA, 1f).setDuration(500)
        val nameTv = ObjectAnimator.ofFloat(bind.tvDetailName, View.ALPHA, 1f).setDuration(500)
        val descTv = ObjectAnimator.ofFloat(bind.tvDetailDescription, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(titleTv, logoutBtn)
            playTogether(photo,dateTv, nameTv, descTv)
            start()
        }
    }

}