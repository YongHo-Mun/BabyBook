package com.yongho.babybook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.yongho.babybook.R
import com.yongho.babybook.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.set_birthday -> {
                launchBirthdayPage()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchBirthdayPage() {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, BirthdayFragment())
            addToBackStack(null)
            commit()
        }
    }
}