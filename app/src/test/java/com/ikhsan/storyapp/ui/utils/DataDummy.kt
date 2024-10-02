package com.ikhsan.storyapp.ui.utils

import com.ikhsan.storyapp.core.data.response.ListStory


object DataDummy {
 
    fun generateStoryResponse(): List<ListStory> {
        val items: MutableList<ListStory> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStory(
                i.toString(),
                "name + $i",
                "desc $i",
                "photo $i",
                "created At $i",
                i.toDouble(),
                i.toDouble(),
            )
            items.add(quote)
        }
        return items
    }
}