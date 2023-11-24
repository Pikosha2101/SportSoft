package com.example.sportsoft.Fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsoft.Adapters.MatchRecyclerAdapter
import com.example.sportsoft.Listener
import com.example.sportsoft.Models.MatchModel
import com.example.sportsoft.R
import com.example.sportsoft.databinding.MatchRegisterFragmentBinding
import java.util.Calendar

class MatchRegisterFragment : Fragment(R.layout.match_register_fragment), Listener<MatchModel>{
    private var _binding : MatchRegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MatchRecyclerAdapter(this)
    private val calendar = Calendar.getInstance()
    private var selectedFromDate: Calendar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchRegisterFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        datePickerCardViewFrom.setOnClickListener {
            showDatePickerFromDialog()
        }
        
        datePickerCardViewTo.setOnClickListener {
            showDatePickerToDialog()
        }

        dateSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по дате", Toast.LENGTH_SHORT).show()
        }

        teamSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по тиме", Toast.LENGTH_SHORT).show()
        }

        scoreSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по счёту", Toast.LENGTH_SHORT).show()
        }

        adapter.setList(
            listOf(
                MatchModel(
                    "22.11.2023",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "23.11.2023",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "24.11.2023",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "25.11.2023",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                ),
                MatchModel(
                    "26.11.2023",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "27.11.2023",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "28.11.2023",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "29.11.2023",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                ),
                MatchModel(
                    "26.11.2023",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "27.11.2023",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "28.11.2023",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "29.11.2023",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                )
            )
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickEdit(param: MatchModel) {
        findNavController().navigate(R.id.action_matchRegisterFragment_to_matchProgressFragment)
    }

    override fun onClickOpen(param: MatchModel) {
        findNavController().navigate(R.id.action_matchRegisterFragment_to_prematchProtocolFragment)
    }

    private fun showDatePickerFromDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                selectedFromDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                val selectedDate = "$dayOfMonth.${month + 1}.$year"
                binding.dateFromEditText.text = selectedDate

                //showDatePickerToDialog()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun showDatePickerToDialog() {
        selectedFromDate?.let { fromDate ->
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$dayOfMonth.${month + 1}.$year"
                    binding.dateToEditText.text = selectedDate
                },
                fromDate.get(Calendar.YEAR),
                fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.datePicker.minDate = fromDate.timeInMillis

            datePickerDialog.show()
        }
    }
}