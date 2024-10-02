package com.ikhsan.storyapp.core.data.repository.di

import android.content.Context
import androidx.room.Room
import com.ikhsan.storyapp.core.room.RemoteKeysDao
import com.ikhsan.storyapp.core.room.StoryDao
import com.ikhsan.storyapp.core.room.StoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideStoryDatabase(@ApplicationContext context: Context): StoryDatabase {
        return Room.databaseBuilder(context, StoryDatabase::class.java, "story_database").build()
    }
}
