package com.ikhsan.storyapp.base.helper

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WrapContentLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        runCatching {
            super.onLayoutChildren(recycler, state)
        }
    }
}

class WrapContentGridLayoutManager(context: Context, span: Int) : GridLayoutManager(context, span) {

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        runCatching {
            super.onLayoutChildren(recycler, state)
        }
    }
}