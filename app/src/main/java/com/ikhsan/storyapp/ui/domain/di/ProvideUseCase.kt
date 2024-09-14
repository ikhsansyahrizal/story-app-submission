package com.ikhsan.storyapp.ui.domain.di

import com.ikhsan.storyapp.ui.domain.auth.AuthUseCase
import com.ikhsan.storyapp.ui.domain.auth.AuthUseCaseImpl
import com.ikhsan.storyapp.ui.domain.story.StoryUseCase
import com.ikhsan.storyapp.ui.domain.story.StoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideUseCase {

    @Binds
    fun provideRegisterUseCase(impl: AuthUseCaseImpl): AuthUseCase

    @Binds
    fun provideStoryUseCase(impl: StoryUseCaseImpl): StoryUseCase

}