package com.example.sportsoft.Fragments

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

class MatchRegisterFragment : Fragment(R.layout.match_register_fragment), Listener<MatchModel>{
    private var _binding : MatchRegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MatchRecyclerAdapter(this)



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
        Toast.makeText(requireContext(), "edit " + param.firstTeamName + "-" + param.secondTeamName, Toast.LENGTH_SHORT).show()
    }

    override fun onClickOpen(param: MatchModel) {
        findNavController().navigate(R.id.action_matchRegisterFragment_to_prematchProtocolFragment)
    }
}