package com.ikhsan.storyapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.lang.ref.WeakReference

abstract class BaseRecycleViewAdapter<T, RV : RecyclerView.ViewHolder>(fragment: Fragment) : RecyclerView.Adapter<RV>() {
    var mlayout: Int? = null
    var items:ArrayList<T> = ArrayList()
    var customSize = 0
    var context: Context? = null

    inline fun <reified T : ViewBinding> inflateViewBinding(parent: ViewGroup): T {
        val layoutInflater = LayoutInflater.from(parent.context)
        return T::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType).invoke(null, layoutInflater, parent, false) as T
    }

    override fun getItemCount(): Int{
        return if (customSize == 0 ) items.size
        else customSize
    }

    open fun add(item: T) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addAll(items: List<T>) {
        for (item in items) {
            add(item)
        }
    }

    fun removeAt(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, itemCount)
    }

    fun editAt(position: Int, newData : T){
        items[position] = newData
        notifyItemChanged(position)
    }

    fun clear() {
        notifyItemRangeRemoved(0, itemCount)
        items.clear()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RV

    override fun onBindViewHolder(holder: RV, position: Int) {
        getBindViewHolder(holder, position, items[position])
    }

    abstract fun getBindViewHolder(holder: RV, position: Int, data: T)

    private val fragmentRef = WeakReference(fragment)

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setupLifecycleObserver(recyclerView)
    }

    private fun setupLifecycleObserver(recyclerView: RecyclerView) {
        val fragment = fragmentRef.get() ?: return
        val weakThis = WeakReference(this)
        val weakRecyclerView = WeakReference(recyclerView)

        fragment.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                val actualRecyclerView = weakRecyclerView.get() ?: return
                when (event.targetState) {
                    Lifecycle.State.DESTROYED -> {
                        actualRecyclerView.adapter = null
                        actualRecyclerView.layoutManager = null
                    }
                    Lifecycle.State.RESUMED -> {
                        val self = weakThis.get() ?: return
                        if (actualRecyclerView.adapter != self) {
                            actualRecyclerView.adapter = self
                        }
                    }
                    else -> {}
                }
            }
        })
    }
}