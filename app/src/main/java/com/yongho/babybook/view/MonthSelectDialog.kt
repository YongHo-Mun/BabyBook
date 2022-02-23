package com.yongho.babybook.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.yongho.babybook.R
import com.yongho.babybook.databinding.MonthPickerBinding
import com.yongho.babybook.viewmodel.BabyBookViewModel
import java.time.LocalDate

class MonthSelectDialog : DialogFragment() {

    private var _binding: MonthPickerBinding? = null
    private val binding get() = _binding!!

    private val currentDate by lazy {
        LocalDate.now()
    }

    private val babyBookViewModel: BabyBookViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate<MonthPickerBinding?>(inflater, R.layout.month_picker, container, false).apply {
            fragment = this@MonthSelectDialog
            viewModel = babyBookViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initYearPicker()
        initMonthPicker()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onOkButtonClicked(year: Int, month: Int) {
        Log.d(TAG, "ok button clicked, year : $year, month: $month")
        babyBookViewModel.selectedMonth.value = LocalDate.of(year, month, DUMMY_DAY_OF_MONTH)
        dismiss()
    }

    fun onCancelButtonClicked() {
        dismiss()
    }

    private fun initYearPicker() {
        binding.yearPicker.minValue = MIN_YEAR
        binding.yearPicker.maxValue = currentDate.year
    }

    private fun initMonthPicker() {
        binding.monthPicker.minValue = MIN_MONTH
        binding.monthPicker.maxValue = MAX_MONTH
    }

    companion object {
        private const val TAG = "MonthSelectDialog"
        private const val MIN_YEAR = 1970
        private const val MIN_MONTH = 1
        private const val MAX_MONTH = 12
        private const val DUMMY_DAY_OF_MONTH = 1
    }
}