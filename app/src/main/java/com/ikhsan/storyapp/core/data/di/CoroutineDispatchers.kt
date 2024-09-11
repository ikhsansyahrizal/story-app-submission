package com.ikhsan.storyapp.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object ProvideCoroutineDispatchers {

    @HeavyWorkThread
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @UIThread
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @IOThread
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @UndefinedThread
    @Provides
    fun provideUndefinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class HeavyWorkThread

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IOThread

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UIThread

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class UndefinedThread