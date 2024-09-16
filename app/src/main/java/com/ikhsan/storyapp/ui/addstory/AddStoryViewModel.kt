package com.ikhsan.storyapp.ui.addstory

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikhsan.storyapp.base.UIStateData
import com.ikhsan.storyapp.base.wrapper.ConsumeResultDomain
import com.ikhsan.storyapp.core.data.request.AddNewStoryReq
import com.ikhsan.storyapp.core.data.response.AddNewStoryRes
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.ui.domain.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(private val useCase: StoryUseCase) : ViewModel() {

    private var _addStory = MutableLiveData<UIStateData<AddNewStoryRes>>()
    val addStotry : LiveData<UIStateData<AddNewStoryRes>> = _addStory

    fun addNewStory(image: MultipartBody.Part, description: RequestBody) = viewModelScope.launch {
        useCase.addNewStory(description = description, image = image)
            .onStart { _addStory.value = UIStateData(loading = true) }
            .collect { data ->
                when(data) {
                    is ConsumeResultDomain.Success -> _addStory.value = UIStateData(data = data.data, loading = false)
                    is ConsumeResultDomain.Error -> _addStory.value = UIStateData(message = data.message, loading = false)
                    is ConsumeResultDomain.ErrorAuth -> _addStory.value = UIStateData(specialMessage = data.message, loading = false)
                }
            }
    }

}