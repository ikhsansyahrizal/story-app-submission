package com.ikhsan.storyapp.core.network

import java.io.IOException

class NoConnectivityException(private val msg: String) : IOException() {
    override var message: String = ""
        get() = msg
}