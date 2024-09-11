package com.ikhsan.storyapp.ui.domain.di

import com.ikhsan.storyapp.ui.domain.RegisterUseCase
import com.ikhsan.storyapp.ui.domain.RegisterUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideUseCase {

    @Binds
    fun provideRegisterUseCase(impl: RegisterUseCaseImpl): RegisterUseCase

}