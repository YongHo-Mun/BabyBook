package com.yongho.babybook.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.R
import com.yongho.babybook.databinding.ImageViewPagerItemBinding

class ImageViewPagerAdapter : RecyclerView.Adapter<ImageViewPagerAdapter.ViewHolder>() {

    private var imageList = arrayOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ImageViewPagerItemBinding>(LayoutInflater.from(parent.context), R.layout.image_view_pager_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    fun setImageList(imageList: Array<Uri>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ImageViewPagerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Uri) {
            binding.image.setImageURI(image)
        }
    }
}