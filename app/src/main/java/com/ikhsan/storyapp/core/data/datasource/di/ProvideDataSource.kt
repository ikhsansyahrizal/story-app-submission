package com.ikhsan.storyapp.core.data.datasource.di

import com.ikhsan.storyapp.core.data.datasource.RegisterDataSource
import com.ikhsan.storyapp.core.data.datasource.RegisterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProvideDataSource {

    @Binds
    fun provideRegisterDataSource(impl: RegisterDataSourceImpl): RegisterDataSource

}