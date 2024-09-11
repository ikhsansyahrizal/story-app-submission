package com.ikhsan.storyapp.base

import com.ikhsan.storyapp.base.helper.remote.RemoteHelper
import com.ikhsan.storyapp.base.helper.remote.RemoteHelperImpl
import com.ikhsan.storyapp.core.network.ApiInterface
import javax.inject.Inject

abstract class BaseRemote: RemoteHelper by RemoteHelperImpl() {

    @Inject
    lateinit var api: ApiInterface

    val errorAuth = "40100"

}