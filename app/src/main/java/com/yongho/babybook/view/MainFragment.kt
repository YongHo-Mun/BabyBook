package com.yongho.babybook.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentMainBinding
import com.yongho.babybook.data.Page
import com.yongho.babybook.data.UserInfoDao
import com.yongho.babybook.viewmodel.BabyBookViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val babyBookListViewModel: BabyBookViewModel by activityViewModels()

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val monthSelectDialog by lazy {
        MonthSelectDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        if (!isBirthdayInputted()) {
            launchBirthdayPage()
            return null
        }

        _binding = DataBindingUtil.inflate<FragmentMainBinding?>(inflater, R.layout.fragment_main, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = babyBookListViewModel
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_fragment_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.set_birthday -> {
                launchBirthdayPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showDeleteDialog(page: Page) {
        activity?.let {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage("Delete selected page?")
                .setNegativeButton("NO") { _, _ -> }
                .setPositiveButton("YES") { _, _ ->
                    babyBookListViewModel.delete(page)
                }

            builder.show()
        }
    }

    fun onAddButtonClickedListener() {
        launchPage(LocalDate.now())
    }

    fun onMonthSelectButtonClickedListener() {
        monthSelectDialog.show(parentFragmentManager, MONTH_SELECT_DIALOG_FRAGMENT_TAG)
    }

    fun launchPage(date: LocalDate) {
        updateCurrentPage(date)

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, PageFragment())
            addToBackStack(null)
            commit()
        }
    }

    private fun initRecyclerView() {
        binding.pageList.setHasFixedSize(true)
    }

    private fun updateCurrentPage(date: LocalDate) {
        Log.d(TAG, "update current date : $date")
        babyBookListViewModel.setCurrentPage(date)
    }

    private fun isBirthdayInputted() : Boolean {
        var birthdayInputted = false

        context?.let {
            val birthday = UserInfoDao.getBirthday(it)
            Log.d(TAG, "birthday : $birthday")

            birthdayInputted = (birthday != null)
        }

        Log.d(TAG, "birthdayInputted : $birthdayInputted")
        return birthdayInputted
    }

    private fun launchBirthdayPage() {
        Log.d(TAG, "launchMainPageList")
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, BirthdayFragment())
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        private const val TAG = "MainFragment"
        private const val MONTH_SELECT_DIALOG_FRAGMENT_TAG = "month_select_dialog"
    }
}

@BindingAdapter(value = ["bind:pages", "bind:fragment"], requireAll = false)
fun setPages(recyclerView: RecyclerView, pages: Array<Page>?, fragment: MainFragment) {

    if (recyclerView.adapter == null) {
        recyclerView.adapter = PageAdapter(object : PageAdapter.PageEventListener {
            override fun onItemClicked(page: Page) {
                fragment.launchPage(page.date)
            }

            override fun onItemLongClicked(page: Page): Boolean {
                fragment.showDeleteDialog(page)
                return true
            }
        })
    }

    pages?.let {
        val adapter = recyclerView.adapter as PageAdapter
        adapter.setPages(it)
    }
}