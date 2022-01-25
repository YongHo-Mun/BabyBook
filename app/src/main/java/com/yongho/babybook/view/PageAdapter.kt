package com.yongho.babybook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.R
import com.yongho.babybook.databinding.PageItemBinding
import com.yongho.babybook.data.Page

class PageAdapter(private val pageEventListener: PageEventListener) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    private var pages = arrayOf<Page>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<PageItemBinding>(LayoutInflater.from(parent.context), R.layout.page_item, parent, false).apply {
            eventListener = pageEventListener
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    fun setPages(pages: Array<Page>) {
        this.pages = pages
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: PageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Page) {
            binding.page = page
        }
    }

    interface PageEventListener {
        fun onItemClicked(page: Page)
        fun onItemLongClicked(page: Page): Boolean
    }
}