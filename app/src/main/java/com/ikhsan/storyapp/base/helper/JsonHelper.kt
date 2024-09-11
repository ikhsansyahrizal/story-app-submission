package com.ikhsan.storyapp.base.helper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String?.fromJson(): T? {
    return try {
        Gson().fromJson<T>(this ?: "", object : TypeToken<T>() {}.type)
    } catch (e: Exception) {
        Gson().fromJson<T>("", object : TypeToken<T>() {}.type)
    }
}