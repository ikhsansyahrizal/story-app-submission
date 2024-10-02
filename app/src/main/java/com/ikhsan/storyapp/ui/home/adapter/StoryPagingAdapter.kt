package com.ikhsan.storyapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ikhsan.storyapp.base.helper.formatToReadableDate
import com.ikhsan.storyapp.base.helper.loadImageFrom
import com.ikhsan.storyapp.core.data.response.ListStory
import com.ikhsan.storyapp.databinding.ItemStoryBinding

class StoryPagingAdapter :
    PagingDataAdapter<ListStory, StoryPagingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onTapItem: ((ListStory) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(private val bind: ItemStoryBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bind(data: ListStory) {
            bind.tvItemName.text = data.name
            bind.tvItemDescription.text = data.description
            bind.tvItemDate.text = data.createdAt?.formatToReadableDate()
            bind.ivItemPhoto.loadImageFrom(data.photoUrl.orEmpty())

            bind.root.setOnClickListener {
                onTapItem?.invoke(data)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStory>() {
            override fun areItemsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStory, newItem: ListStory): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}