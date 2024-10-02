package com.ikhsan.storyapp.ui.home

import android.os.Parcelable
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.ui.domain.auth.AuthUseCase
import com.ikhsan.storyapp.ui.domain.story.StoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyUseCase: StoryUseCase,
    private val authUseCase: AuthUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _refreshTrigger = MutableLiveData(0)
    private val _stories = _refreshTrigger.switchMap {
        liveData {
            emitSource(storyUseCase.getAllStories().cachedIn(viewModelScope))
        }
    }
    val stories: LiveData<PagingData<ListStory>> get() = _stories

    var recyclerViewState: Parcelable?
        get() = savedStateHandle.get<Parcelable>(KEY_RECYCLER_STATE)
        set(value) {
            savedStateHandle[KEY_RECYCLER_STATE] = value
        }

    fun refresh() {
        _refreshTrigger.value = (_refreshTrigger.value ?: 0) + 1
    }

    fun doLogOut() {
        viewModelScope.launch {
            authUseCase.doLogOut()
        }
    }

    companion object {
        private const val KEY_RECYCLER_STATE = "recycler_state"
    }
}
