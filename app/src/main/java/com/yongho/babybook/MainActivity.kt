package com.yongho.babybook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.yongho.babybook.databinding.ActivityMainBinding
import com.yongho.babybook.entity.Page
import com.yongho.babybook.viewmodel.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val pageViewModel: PageViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        setBtnListener()
    }

    private fun initRecyclerView() {
        val adapter = PageAdapter({ page ->
            val intent = Intent(this, PageActivity::class.java).apply {
                putExtra(PageActivity.EXTRA_PAGE_DATE, page.date)
                putExtra(PageActivity.EXTRA_PAGE_CONTENT, page.content)
            }

            startActivity(intent)

        }, { page ->
            showDeleteDialog(page)
        })

        binding.pageList.adapter = adapter
        binding.pageList.layoutManager = LinearLayoutManager(this)
        binding.pageList.setHasFixedSize(true)

        pageViewModel.getAll().observe(this) { pages ->
            Log.d(TAG, "page list is changed")
            adapter.setPages(pages)
        }
    }

    private fun setBtnListener() {
        binding.addButton.setOnClickListener {
            startActivity(Intent(this, PageActivity::class.java))
        }
    }

    private fun showDeleteDialog(page: Page) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected page?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                pageViewModel.delete(page)
            }

        builder.show()
    }

    companion object {
        private const val TAG = "BabyBook"
    }
}