package com.yongho.babybook.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    fun onDoneButtonClickedListener(date: String, content: String) {
        Log.d(TAG, "date: $date, content: $content")
        pageViewModel.insert(Page(date, content))
        parentFragmentManager.popBackStack()
    }

    companion object {
        const val TAG = "PageFragment"
    }
}