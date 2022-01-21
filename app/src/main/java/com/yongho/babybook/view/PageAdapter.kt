package com.yongho.babybook.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.databinding.PageItemBinding
import com.yongho.babybook.data.Page

class PageAdapter(private val pageItemClick: (Page) -> Unit, private val pageItemLongClick: (Page) -> Unit) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    private var pages = arrayOf<Page>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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