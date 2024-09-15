package com.ikhsan.storyapp.ui.home.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ikhsan.storyapp.base.BaseRecycleViewAdapter
import com.ikhsan.storyapp.base.helper.formatToReadableDate
import com.ikhsan.storyapp.base.helper.loadImageFrom
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.databinding.ItemStoryBinding

class StoryAdapter(fragment: Fragment): BaseRecycleViewAdapter<ListStory, StoryAdapter.ViewHolder>(fragment) {

    var onTapItem: ((ListStory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.ViewHolder {
        return ViewHolder(inflateViewBinding(parent))
    }

    override fun getBindViewHolder(
        holder: StoryAdapter.ViewHolder,
        position: Int,
        data: ListStory
    ) {
        holder.binding(data)
    }

    inner class ViewHolder(private val bind: ItemStoryBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun binding(data: ListStory){
            bind.tvItemName.text = data.name
            bind.tvItemDescription.text = data.description
            bind.tvItemDate.text = data.createdAt?.formatToReadableDate()
            bind.ivItemPhoto.loadImageFrom(data.photoUrl.orEmpty())

            bind.root.setOnClickListener {
                onTapItem?.invoke(data)
            }
       }
    }
}