package com.yongho.babybook.view

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentPageBinding
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageFragment : Fragment() {

    private val pageViewModel : PageViewModel by activityViewModels()

    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        when(activityResult.resultCode) {
            RESULT_OK -> {
                activityResult.data?.let { intent ->
                    val imageList = arrayListOf<Uri>()

                    intent.data?.let { uri ->
                        imageList.add(uri)
                    } ?: intent.clipData?.let { clipData ->
                        val size = clipData.itemCount

                        for (idx in 0 until size) {
                            imageList.add(clipData.getItemAt(idx).uri)
                        }
                    }

                    setImages(imageList)
                }
            }

            RESULT_CANCELED -> {
                Log.d(TAG, "image select canceled")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_page, container, false)
        _binding?.apply {
            Log.d(TAG, "initialize binding")
            viewModel = pageViewModel
            lifecycleOwner = viewLifecycleOwner
            fragment = this@PageFragment
            imageViewPager.adapter = ImageViewPagerAdapter()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDoneButtonClicked(date: String, content: String?) {
        Log.d(TAG, "onDoneButtonClicked")
        Log.d(TAG, "date: $date, content: $content")
        pageViewModel.currentPage.date = date
        pageViewModel.currentPage.content = content
        pageViewModel.saveCurrentPage()
        parentFragmentManager.popBackStack()
    }

    fun onViewPagerClicked() {
        Log.d(TAG, "onViewPagerClicked")
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
            data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }

        galleryLauncher.launch(intent)
    }

    private fun setImages(imageList: ArrayList<Uri>) {
        if (imageList.isNotEmpty()) {
            val imageAdapter = binding.imageViewPager.adapter as ImageViewPagerAdapter
            imageAdapter.setImageList(imageList)
            binding.emptyImage.visibility = View.INVISIBLE

            pageViewModel.currentPage.imageList = imageList
        } else {
            binding.emptyImage.visibility = View.VISIBLE
        }
    }

    companion object {
        const val TAG = "PageFragment"
    }
}

@BindingAdapter(value = ["bind:images"], requireAll = false)
fun setImages(viewPager: ViewPager2, images: List<Uri>?) {
    images?.let {
        val adapter = viewPager.adapter as ImageViewPagerAdapter
        adapter.setImageList(images)
    }
}