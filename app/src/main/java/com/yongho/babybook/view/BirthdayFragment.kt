package com.yongho.babybook.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.yongho.babybook.R
import com.yongho.babybook.databinding.FragmentBirthdayBinding
import java.util.*

class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_birthday, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@BirthdayFragment
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDoneButtonClicked(date: Long) {
        Log.d(TAG, "selected date : $date")
        saveBirthday(date)
        parentFragmentManager.popBackStack()
    }

    private fun saveBirthday(birthday: Long) {
        activity?.let {
            val sharedPreferencesEditor = it.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
            sharedPreferencesEditor.putLong(SHARED_PREFERENCES_BIRTH_DAY_KEY, birthday)
            sharedPreferencesEditor.commit()
        }
    }

    companion object {
        const val TAG = "BirthdayFragment"
        const val SHARED_PREFERENCES_NAME = "Birthday"
        const val SHARED_PREFERENCES_BIRTH_DAY_KEY = "birthday"
        const val SHARED_PREFERENCES_BIRTH_DAY_DEFAULT_VALUE = -1L
    }
}