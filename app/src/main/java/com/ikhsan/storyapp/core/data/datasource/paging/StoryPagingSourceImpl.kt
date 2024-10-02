package com.ikhsan.storyapp.core.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.core.network.ApiInterface
import javax.inject.Inject

class StoryPagingSourceImpl(
    private val apiService: ApiInterface,
) : PagingSource<Int, ListStory>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStory> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getAllStories(
                page = position,
                size = params.loadSize,
                location = position
            )

            val stories = response.body()?.listStory ?: emptyList()

            LoadResult.Page(
                data = stories,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (stories.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
