package com.ikhsan.storyapp.core.data.repository.di

import com.ikhsan.storyapp.core.data.repository.RegisterRepository
import com.ikhsan.storyapp.core.data.repository.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideRepository {

    @Binds
    fun provideRegisterRepository(impl: RegisterRepositoryImpl): RegisterRepository

}