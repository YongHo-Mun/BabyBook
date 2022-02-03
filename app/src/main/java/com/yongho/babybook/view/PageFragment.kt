package com.yongho.babybook.view

import android.Manifest
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.yongho.babybook.R
import com.yongho.babybook.data.Page
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
                val imageList = extractImageUriList(activityResult)
                setImages(imageList)
            }

            RESULT_CANCELED -> {
                Log.d(TAG, "image select canceled")
            }
        }
    }

    private val galleryLauncherBasedOnPermissionCheck = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        Log.d(TAG, "isGranted: $isGranted")

        if (isGranted) {
            Log.d(TAG, "launchGallery")
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = MediaStore.Images.Media.CONTENT_TYPE
                data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }

            galleryLauncher.launch(intent)
        } else {
            Toast.makeText(context, R.string.need_storage_permission, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.page_fragment_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.set_image -> {
                launchGallery()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onDoneButtonClicked(date: String, content: String, imageList: List<Uri>) {
        Log.d(TAG, "onDoneButtonClicked")
        Log.d(TAG, "date: $date, content: $content")

        pageViewModel.currentPage.value = Page(date, content, imageList)
        pageViewModel.saveCurrentPage()
        parentFragmentManager.popBackStack()
    }

    fun refreshImageViewPagerIndicator() {
        binding.imageViewPagerIndicator.setViewPager(binding.imageViewPager)
    }

    private fun launchGallery() {
        galleryLauncherBasedOnPermissionCheck.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun extractImageUriList(activityResult: ActivityResult): List<Uri> {
        val imageList = arrayListOf<Uri>()

        activityResult.data?.let { intent ->
            intent.data?.let { uri ->
                Log.d(TAG, "uri: $uri")
                imageList.add(uri)
            } ?: intent.clipData?.let { clipData ->
                val size = clipData.itemCount

                for (idx in 0 until size) {
                    Log.d(TAG, "uri: ${clipData.getItemAt(idx).uri}")
                    imageList.add(clipData.getItemAt(idx).uri)
                }
            }
        }

        return imageList
    }
    private fun setImages(imageList: List<Uri>) {
        if (imageList.isNotEmpty()) {
            val adapter = binding.imageViewPager.adapter as ImageViewPagerAdapter
            adapter.setImageList(imageList)
            refreshImageViewPagerIndicator()
        }
    }

    companion object {
        const val TAG = "PageFragment"
    }
}

@BindingAdapter(value = ["bind:images", "bind:fragment"], requireAll = false)
fun setImages(viewPager: ViewPager2, images: List<Uri>, fragment: PageFragment) {
    val adapter = viewPager.adapter as ImageViewPagerAdapter
    adapter.setImageList(images)

    fragment.refreshImageViewPagerIndicator()
}