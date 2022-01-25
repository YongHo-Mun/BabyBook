package com.yongho.babybook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentMainBinding
import com.yongho.babybook.data.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val pageListViewModel: PageViewModel by activityViewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = pageListViewModel
            fragment = this@MainFragment
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showDeleteDialog(page: Page) {
        activity?.let {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage("Delete selected page?")
                .setNegativeButton("NO") { _, _ -> }
                .setPositiveButton("YES") { _, _ ->
                    pageListViewModel.delete(page)
                }

            builder.show()
        }
    }

    fun onAddButtonClickedListener() {
        launchPage(LocalDate.now().toString())
    }

    fun launchPage(date: String) {
        updateCurrentPage(date)

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, PageFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun initRecyclerView() {
        binding.pageList.setHasFixedSize(true)
    }

    private fun updateCurrentPage(date: String) {
        pageListViewModel.setCurrentPage(date)
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}

@BindingAdapter(value = ["bind:pages", "bind:fragment"], requireAll = false)
fun setPages(recyclerView: RecyclerView, pages: Array<Page>?, fragment: MainFragment) {

    if (recyclerView.adapter == null) {
        recyclerView.adapter = PageAdapter({ page ->
            fragment.launchPage(page.date)

        }, { page ->
            fragment.showDeleteDialog(page)
        })
    }

    pages?.let {
        val adapter = recyclerView.adapter as PageAdapter
        adapter.setPages(it)
    }
}