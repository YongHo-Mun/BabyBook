package com.yongho.babybook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yongho.babybook.databinding.FragmentPageBinding
import com.yongho.babybook.entity.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class PageFragment(private val dateText: String) : Fragment() {

    private val pageViewModel : PageViewModel by viewModels()

    private var _binding: FragmentPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInitData()
        setBtnListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadInitData() {
        pageViewModel.getContentByDate(dateText).observe(this) {
            Log.d(TAG, "loaded content : $it")
            binding.page = Page(dateText, it)
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            val date = binding.date.text.toString()
            val content = binding.content.text.toString()

            pageViewModel.insert(Page(date, content))
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        const val TAG = "PageFragment"
    }
}