package com.ikhsan.storyapp.core.data.datasource.di

import com.ikhsan.storyapp.core.data.datasource.auth.AuthDataSource
import com.ikhsan.storyapp.core.data.datasource.auth.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideDataSource {

    @Binds
    fun provideRegisterDataSource(impl: AuthDataSourceImpl): AuthDataSource

}