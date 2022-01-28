package com.yongho.babybook.view

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentPageBinding
import com.yongho.babybook.data.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PageFragment : Fragment() {

    private val pageViewModel : PageViewModel by activityViewModels()

    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when(it.resultCode) {
            RESULT_OK -> {
                Log.d(TAG, "selected image : ${it.data?.data}")
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
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDoneButtonClicked(date: String, content: String) {
        Log.d(TAG, "date: $date, content: $content")
        pageViewModel.insert(Page(date, content))
        parentFragmentManager.popBackStack()
    }

    fun onImageButtonClicked() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = MediaStore.Images.Media.CONTENT_TYPE
        }

        galleryLauncher.launch(intent)
    }

    companion object {
        const val TAG = "PageFragment"
    }
}