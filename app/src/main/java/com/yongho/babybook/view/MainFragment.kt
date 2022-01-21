package com.yongho.babybook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentMainBinding
import com.yongho.babybook.data.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val pageListViewModel: PageViewModel by viewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setBtnListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        val adapter = PageAdapter({ page ->
            launchPage(page.date)

        }, { page ->
            showDeleteDialog(page)
        })

        binding.pageList.adapter = adapter
        binding.pageList.layoutManager = LinearLayoutManager(activity)
        binding.pageList.setHasFixedSize(true)

        pageListViewModel.getAll().observe(this) { pages ->
            Log.d(TAG, "page list is changed")
            adapter.setPages(pages)
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            launchPage(LocalDate.now().toString())
        }
    }

    private fun showDeleteDialog(page: Page) {
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

    private fun launchPage(date: String) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, PageFragment(date))
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val TAG = "MainFragment"
    }
}