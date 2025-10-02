package com.jmadrigal.hackernews.features.news.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding.view.RxView
import com.jmadrigal.hackernews.R
import com.jmadrigal.hackernews.databinding.ItemNewsBinding
import com.jmadrigal.hackernews.features.news.data.model.Hit
import com.jmadrigal.hackernews.utils.OnItemSelected
import com.jmadrigal.hackernews.utils.formatDate
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class NewsAdapter(private var itemSelected: OnItemSelected<Hit>) : ListAdapter<Hit, NewsAdapter.ViewHolder>(DiffUtilsCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.txtTitle.text = this.storyTitle
                binding.txtUpdatedAt.text = holder.binding.root.context.getString(R.string.news_subtitle, this.author, this.createdAt?.formatDate())

                RxView.clicks(binding.item)
                    .throttleFirst(100, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        itemSelected(this)
                    }
            }
        }
    }

    inner class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    private class DiffUtilsCallback() : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean =
            oldItem.objectID == newItem.objectID

    }
}