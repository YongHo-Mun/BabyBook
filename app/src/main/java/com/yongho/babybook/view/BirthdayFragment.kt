package com.yongho.babybook.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.databinding.DataBindingUtil
import com.yongho.babybook.R
import com.yongho.babybook.data.UserInfoDao
import com.yongho.babybook.databinding.FragmentBirthdayBinding
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class BirthdayFragment : Fragment() {

    private var _binding: FragmentBirthdayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        Log.d(TAG, "onCreateView")

        _binding = DataBindingUtil.inflate<FragmentBirthdayBinding?>(inflater, R.layout.fragment_birthday, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@BirthdayFragment

            calendar.setOnDateChangeListener { calendarView: CalendarView, year: Int, month: Int, dayOfMonth: Int ->
                val epochMillis = TimeUnit.DAYS.toMillis(LocalDate.of(year, month + 1, dayOfMonth).toEpochDay())
                calendarView.date = epochMillis
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onDoneButtonClicked(selectedDate: Long) {
        val epochDays = TimeUnit.MILLISECONDS.toDays(selectedDate)
        val selectedLocalDate = LocalDate.ofEpochDay(epochDays)

        saveBirthday(selectedLocalDate)
        parentFragmentManager.popBackStack()
    }

    private fun saveBirthday(birthday: LocalDate) {
        context?.let {
            Log.d(TAG, "save birthday : $birthday")
            UserInfoDao.setBirthday(it, birthday)
        }
    }

    companion object {
        const val TAG = "BirthdayFragment"
    }
}