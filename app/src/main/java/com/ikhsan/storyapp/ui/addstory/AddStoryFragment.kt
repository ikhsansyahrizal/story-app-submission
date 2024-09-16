package com.ikhsan.storyapp.ui.addstory

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.ikhsan.storyapp.base.helper.getImageUri
import com.ikhsan.storyapp.base.helper.getTexts
import com.ikhsan.storyapp.base.helper.observe
import com.ikhsan.storyapp.base.helper.reduceFileImage
import com.ikhsan.storyapp.base.helper.uriToFile
import com.ikhsan.storyapp.databinding.FragmentAddStoryBinding
import com.ikhsan.storyeapp.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>(FragmentAddStoryBinding::inflate) {
    private var currentImageUri: Uri? = null
    private val viewModel: AddStoryViewModel by viewModels()

    override fun initView() {
        playAnimation()
    }

    override fun initListener() {
        bind.buttonGallery.setOnClickListener {
            startGallery()
        }

        bind.buttonCamera.setOnClickListener {
            startCamera()
        }

        bind.buttonAdd.setOnClickListener {
            uploadData()
        }

    }

    override fun initObserver() {
        observe(viewModel.addStotry) { data ->
            data?.data?.let { gooTo(AddStoryFragmentDirections.toHome()) }
            data?.specialMessage?.forceLogout()
            data?.message?.let { displayInfoMessage(it) }
            data?.loading?.let { showLoadingDialog(it) }
        }

    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        } else {
            currentImageUri = null
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            bind.previewImageView.setImageURI(it)
        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun uploadData() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val requestDescription =
                bind.edAddDescription.getTexts().toRequestBody("text/plain".toMediaType())

            viewModel.addNewStory(image = multipartBody, description = requestDescription)

        } ?: Toast.makeText(requireContext(), "Please input image first", Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        val photo = ObjectAnimator.ofFloat(bind.previewImageView, View.ALPHA, 1f).setDuration(500)

        val addTv = ObjectAnimator.ofFloat(bind.tvAddImage, View.ALPHA, 1f).setDuration(500)
        val cameraBtn = ObjectAnimator.ofFloat(bind.buttonCamera, View.ALPHA, 1f).setDuration(500)
        val galleryBtn = ObjectAnimator.ofFloat(bind.buttonGallery, View.ALPHA, 1f).setDuration(500)

        val descLayout =
            ObjectAnimator.ofFloat(bind.textInputLayoutDescription, View.ALPHA, 1f).setDuration(500)
        val descEt = ObjectAnimator.ofFloat(bind.edAddDescription, View.ALPHA, 1f).setDuration(500)

        val addBtn = ObjectAnimator.ofFloat(bind.buttonAdd, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playTogether(photo, addTv, cameraBtn, galleryBtn)
            playSequentially(descLayout, descEt, addBtn)
            start()
        }
    }
}