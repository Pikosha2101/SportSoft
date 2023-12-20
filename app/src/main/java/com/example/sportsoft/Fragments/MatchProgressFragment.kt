package com.example.sportsoft.Fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sportsoft.CountUpTimer
import com.example.sportsoft.R
import com.example.sportsoft.databinding.MatchProgressFragmentBinding

class MatchProgressFragment : Fragment(R.layout.match_progress_fragment) {
    private var _binding: MatchProgressFragmentBinding? = null
    private val binding get() = _binding!!

    private var isTimerRunning = false
    private var isTimerPaused = false
    private var secondsRemaining: Long = 0

    private lateinit var countUpTimer: CountUpTimer
    private lateinit var handler: Handler
    private var eventsList = listOf("Гол", "Желтая карточка", "Красная карточка")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchProgressFragmentBinding.inflate(inflater, container, false)
        handler = Handler(Looper.getMainLooper())
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        startCardView.setOnClickListener {
            if (!isTimerRunning && !isTimerPaused) {
                startTimer()
            } else if (isTimerPaused) {
                resumeTimer()
            }
        }

        stopCardView.setOnClickListener {
            stopTimer()
        }

        pauseCardView.setOnClickListener {
            pauseTimer()
        }

        finishCardView.setOnClickListener {
            findNavController().navigate(R.id.action_matchProgressFragment_to_matchRegisterFragment)
        }

        firstTeamFoulsCountMinusFirstTimeImageButton.setOnClickListener {
            if (firstTeamFoulsCountFirstTimeTextView.text.isNotEmpty() && firstTeamFoulsCountFirstTimeTextView.text.toString().toInt() > 0){
                val score = firstTeamFoulsCountFirstTimeTextView.text.toString().toInt()
                firstTeamFoulsCountFirstTimeTextView.text = (score - 1).toString()
            }
        }

        firstTeamFoulsCountPlusFirstTimeImageButton.setOnClickListener {
            if (firstTeamFoulsCountFirstTimeTextView.text.isNotEmpty()){
                val score = firstTeamFoulsCountFirstTimeTextView.text.toString().toInt()
                firstTeamFoulsCountFirstTimeTextView.text = (score + 1).toString()
            } else {
                firstTeamFoulsCountFirstTimeTextView.text = (1).toString()
            }
        }

        secondTeamFoulsCountMinusFirstTimeImageButton.setOnClickListener {
            if (secondTeamFoulsCountFirstTimeTextView.text.isNotEmpty() && secondTeamFoulsCountFirstTimeTextView.text.toString().toInt() > 0){
                val score = secondTeamFoulsCountFirstTimeTextView.text.toString().toInt()
                secondTeamFoulsCountFirstTimeTextView.text = (score - 1).toString()
            }
        }

        secondTeamFoulsCountPlusFirstTimeImageButton.setOnClickListener {
            if (secondTeamFoulsCountFirstTimeTextView.text.isNotEmpty()){
                val score = secondTeamFoulsCountFirstTimeTextView.text.toString().toInt()
                secondTeamFoulsCountFirstTimeTextView.text = (score + 1).toString()
            } else {
                secondTeamFoulsCountFirstTimeTextView.text = (1).toString()
            }
        }

        firstTeamFoulsCountMinusSecondTimeImageButton.setOnClickListener {
            if (firstTeamFoulsCountSecondTimeTextView.text.isNotEmpty() && firstTeamFoulsCountSecondTimeTextView.text.toString().toInt() > 0){
                val score = firstTeamFoulsCountSecondTimeTextView.text.toString().toInt()
                firstTeamFoulsCountSecondTimeTextView.text = (score - 1).toString()
            }
        }

        firstTeamFoulsCountPlusSecondTimeImageButton.setOnClickListener {
            if (firstTeamFoulsCountSecondTimeTextView.text.isNotEmpty()){
                val score = firstTeamFoulsCountSecondTimeTextView.text.toString().toInt()
                firstTeamFoulsCountSecondTimeTextView.text = (score + 1).toString()
            } else {
                firstTeamFoulsCountSecondTimeTextView.text = (1).toString()
            }
        }

        secondTeamFoulsCountMinusSecondTimeImageButton.setOnClickListener {
            if (secondTeamFoulsCountSecondTimeTextView.text.isNotEmpty() && secondTeamFoulsCountSecondTimeTextView.text.toString().toInt() > 0){
                val score = secondTeamFoulsCountSecondTimeTextView.text.toString().toInt()
                secondTeamFoulsCountSecondTimeTextView.text = (score - 1).toString()
            }
        }

        secondTeamFoulsCountPlusSecondTimeImageButton.setOnClickListener {
            if (secondTeamFoulsCountSecondTimeTextView.text.isNotEmpty()){
                val score = secondTeamFoulsCountSecondTimeTextView.text.toString().toInt()
                secondTeamFoulsCountSecondTimeTextView.text = (score + 1).toString()
            } else {
                secondTeamFoulsCountSecondTimeTextView.text = (1).toString()
            }
        }

        eventTimePlusImageButton.setOnClickListener {
            if (eventTimeEditText.text.isNotEmpty()){
                val value = eventTimeEditText.text.toString().toInt()
                eventTimeEditText.text = Editable.Factory.getInstance().newEditable((value + 1).toString())
            } else {
                eventTimeEditText.text = Editable.Factory.getInstance().newEditable((1).toString())
            }
        }

        eventTimeMinusImageButton.setOnClickListener {
            val value = eventTimeEditText.text.toString().toInt()
            if (eventTimeEditText.text.isNotEmpty() && value > 0){
                eventTimeEditText.text = Editable.Factory.getInstance().newEditable((value - 1 ).toString())
            }
        }

        menuImageButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it, Gravity.END, 0, R.style.PopupMenuStyle)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.matchRegister -> {
                        findNavController().navigate(R.id.action_matchProgressFragment_to_matchRegisterFragment)
                        true
                    }
                    R.id.exit -> {
                        findNavController().navigate(R.id.action_matchProgressFragment_to_authorizationFragment)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }




        val eventSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, eventsList)
        eventSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        eventSpinner.adapter = eventSpinnerAdapter

        eventSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                when (eventsList[position]){
                    "Гол" -> {
                        goalHiddenConstraintLayout.visibility = View.VISIBLE
                        yellowCardHiddenConstraintLayout.visibility = View.GONE
                    }
                    "Желтая карточка" -> {
                        goalHiddenConstraintLayout.visibility = View.GONE
                        yellowCardHiddenConstraintLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }

    }



    private fun startTimer() {
        if (!isTimerRunning) {
            countUpTimer = object : CountUpTimer(1000) {
                override fun onCountUpTick(elapsedTime: Long) {
                    secondsRemaining = elapsedTime / 1000
                    updateTimerText()
                }
            }
        }
        countUpTimer.startTimer()
        isTimerRunning = true
        isTimerPaused = false
    }



    private fun resumeTimer() {
        if (isTimerPaused) {
            countUpTimer.startTimer()
            isTimerRunning = true
            isTimerPaused = false
        }
    }



    private fun stopTimer() {
        if (isTimerRunning || isTimerPaused) {
            countUpTimer.cancel()
            isTimerRunning = false
            isTimerPaused = false
            secondsRemaining = 0
            updateTimerText()
        }
    }



    private fun pauseTimer() {
        if (isTimerRunning) {
            countUpTimer.cancel()
            isTimerRunning = false
            isTimerPaused = true
            updateTimerText()
        }
    }



    private fun updateTimerText() {
        val minutes = secondsRemaining / 60
        val seconds = secondsRemaining % 60
        val timeString = String.format("%02d:%02d", minutes, seconds)
        binding.timerTextView.text = timeString
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}