package com.ikhsan.storyapp.core.data.repository.di

import com.ikhsan.storyapp.core.data.mediator.StoryRemoteMediator
import com.ikhsan.storyapp.core.network.ApiInterface
import com.ikhsan.storyapp.core.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StoryMediatorModule {

    @Singleton
    @Provides
    fun provideStoryRemoteMediator(
        storyDatabase: StoryDatabase,
        apiService: ApiInterface
    ): StoryRemoteMediator {
        return StoryRemoteMediator(storyDatabase, apiService)
    }
}
