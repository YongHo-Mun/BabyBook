package com.yongho.babybook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.databinding.PageItemBinding
import com.yongho.babybook.entity.Page

class PageAdapter(private val pageItemClick: (Page) -> Unit, private val pageItemLongClick: (Page) -> Unit) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    private var pages = listOf<Page>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageAdapter.ViewHolder {
        val binding = PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageAdapter.ViewHolder, position: Int) {
        holder.bind(pages[position])
    }

    override fun getItemCount(): Int {
        return pages.size
    }

    fun setPages(pages: List<Page>) {
        this.pages = pages
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: PageItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Page) {
            binding.page = page

            itemView.setOnClickListener {
                pageItemClick(page)
            }

            itemView.setOnLongClickListener {
                pageItemLongClick(page)
                true
            }
        }
    }
}