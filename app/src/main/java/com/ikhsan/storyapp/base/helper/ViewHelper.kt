package com.ikhsan.storyapp.base.helper

import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ikhsan.storyapp.R
import com.bumptech.glide.Priority
import com.ikhsan.storyapp.base.UIConstant.DD_MMMM_YYYY_HH_MM_S
import com.ikhsan.storyapp.base.UIConstant.DMY
import java.text.SimpleDateFormat
import java.util.Locale


fun EditText.getTexts() = text.toString()

fun ImageView.loadImageFrom(imageUrl: String) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.img_no_image)
        .error(R.drawable.img_no_image)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .priority(Priority.HIGH)
    Glide.with(this.context)
        .load(imageUrl)
        .apply(options)
        .into(this)
}

fun <RV : RecyclerView.ViewHolder?> RecyclerView.initRecycleView(
    adapterRV: RecyclerView.Adapter<RV>,
    isVertical: Boolean = true,
    isReverse: Boolean = false,
    fixedSize: Boolean = false,
    cacheRv: Boolean = false,
    customLinearLayoutManager: LinearLayoutManager? = null,
) =
    this.apply {
        layoutManager = customLinearLayoutManager ?: LinearLayoutManager(
            this.context,
            if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
            isReverse
        )
        adapter = adapterRV
        setHasFixedSize(fixedSize)
        if (cacheRv) setItemViewCacheSize(25)

    }

fun String.formatToReadableDate(): String {
    val inputFormat = SimpleDateFormat(DD_MMMM_YYYY_HH_MM_S, Locale.getDefault())
    val outputFormat = SimpleDateFormat(DMY, Locale("id", "ID"))

    return try {
        val date = inputFormat.parse(this)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        this
    }
}

fun SwipeRefreshLayout.onSwipeListener(callback: () -> Unit) = this.setOnRefreshListener {
    callback()
    this.isRefreshing = false
}