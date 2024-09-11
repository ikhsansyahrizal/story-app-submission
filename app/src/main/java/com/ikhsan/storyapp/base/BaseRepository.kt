package com.ikhsan.storyapp.base

import com.ikhsan.storyapp.core.data.di.IOThread
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

abstract class BaseRepository {

//    @Inject
//    lateinit var cacheManager: CacheManager

    @Inject
    @IOThread
    lateinit var ioDispatcher: CoroutineDispatcher
}