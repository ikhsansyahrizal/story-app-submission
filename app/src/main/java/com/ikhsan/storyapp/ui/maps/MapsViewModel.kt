package com.ikhsan.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ikhsan.storyapp.ui.domain.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val useCase : StoryUseCase,
    ): ViewModel() {

    fun getAllStories() = useCase.getAllStoriesWithLocation().cachedIn(viewModelScope)
}