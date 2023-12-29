package com.example.sportsoft.Fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sportsoft.CountUpTimer
import com.example.sportsoft.R
import com.example.sportsoft.R.color.blue
import com.example.sportsoft.databinding.MatchProgressFragmentBinding

class MatchProgressFragment : Fragment(R.layout.match_progress_fragment) {
    private var _binding: MatchProgressFragmentBinding? = null
    private val binding get() = _binding!!

    private var isTimerRunning = false
    private var isTimerPaused = false
    private var secondsRemaining: Long = 0

    private lateinit var countUpTimer: CountUpTimer
    private var eventsList = listOf("Гол", "Желтая карточка", "Красная карточка")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchProgressFragmentBinding.inflate(inflater, container, false)
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

            startCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
            startTimeConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            startTimerTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
            startImageView.setImageResource(R.drawable.arrow_start_icon_unavailable)

            pauseConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
            pauseCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
            pauseButtonImageView.setImageResource(R.drawable.pause_availabe_icon)
            pauseTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))

            stopConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
            stopCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
            stopTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            stopButtonImageView.setImageResource(R.drawable.stop_available_icon)
        }

        stopCardView.setOnClickListener {
            stopTimer()
            startCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
            startTimeConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
            startTimerTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            startImageView.setImageResource(R.drawable.arrow_start_icon)

            stopConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            stopCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
            stopTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
            stopButtonImageView.setImageResource(R.drawable.stop_unavailable_icon)

            pauseConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            pauseCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
            pauseButtonImageView.setImageResource(R.drawable.pause_unavailabe_icon)
            pauseTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
        }

        pauseCardView.setOnClickListener {
            pauseTimer()
            startCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
            startTimeConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
            startTimerTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            startImageView.setImageResource(R.drawable.arrow_start_icon)

            pauseConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            pauseCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
            pauseButtonImageView.setImageResource(R.drawable.pause_unavailabe_icon)
            pauseTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
        }


        finishCardView.setOnClickListener {
            findNavController().navigate(R.id.action_matchProgressFragment_to_matchRegisterFragment)
        }

        firstTeamFoulsCountMinusFirstTimeImageButton.setOnClickListener {
            handleButtonClick(firstTeamFoulsCountFirstTimeTextView, increment = false, 0)
        }

        firstTeamFoulsCountPlusFirstTimeImageButton.setOnClickListener {
            handleButtonClick(firstTeamFoulsCountFirstTimeTextView, increment = true)
        }

        secondTeamFoulsCountMinusFirstTimeImageButton.setOnClickListener {
            handleButtonClick(secondTeamFoulsCountFirstTimeTextView, increment = false, 0)
        }

        secondTeamFoulsCountPlusFirstTimeImageButton.setOnClickListener {
            handleButtonClick(secondTeamFoulsCountFirstTimeTextView, increment = true)
        }

        firstTeamFoulsCountMinusSecondTimeImageButton.setOnClickListener {
            handleButtonClick(firstTeamFoulsCountSecondTimeTextView, increment = false, 0)
        }

        firstTeamFoulsCountPlusSecondTimeImageButton.setOnClickListener {
            handleButtonClick(firstTeamFoulsCountSecondTimeTextView, increment = true)
        }

        secondTeamFoulsCountMinusSecondTimeImageButton.setOnClickListener {
            handleButtonClick(secondTeamFoulsCountSecondTimeTextView, increment = false, 0)
        }

        secondTeamFoulsCountPlusSecondTimeImageButton.setOnClickListener {
            handleButtonClick(secondTeamFoulsCountSecondTimeTextView, increment = true)
        }

        eventTimePlusImageButton.setOnClickListener {
            handleButtonClick(eventTimeEditText, increment = true)
        }

        eventTimeMinusImageButton.setOnClickListener {
            handleButtonClick(eventTimeEditText, increment = false, 0)
        }

       /* redCardSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                eventTimeRedCardEditText.focusable = View.NOT_FOCUSABLE
                eventTimeRedCardEditText.isClickable = false
            } else {
                eventTimeRedCardEditText.focusable = View.FOCUSABLE
                eventTimeRedCardEditText.isClickable = true
            }
        }*/

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
                val selectedFruit = eventsList[position]
                when (selectedFruit){
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



    private fun handleButtonClick(
        textView: TextView,
        increment: Boolean,
        minValue: Int = 0
    ) {
        if (textView.text.isNotEmpty()) {
            val value = textView.text.toString().toInt()
            textView.text = (if (increment) value + 1 else value - 1).coerceAtLeast(minValue).toString()
        } else {
            textView.text = (if (increment) 1 else minValue).toString()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}