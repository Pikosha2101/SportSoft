package com.example.sportsoft.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sportsoft.CountUpTimer
import com.example.sportsoft.R
import com.example.sportsoft.R.color.blue
import com.example.sportsoft.databinding.MatchProgressFragmentBinding
import com.example.sportsoft.viewModels.MatchProgressViewModel
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class MatchProgressFragment : Fragment(R.layout.match_progress_fragment) {
    private var _binding: MatchProgressFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MatchProgressViewModel
    private var isTimerRunning = false
    private var secondsRemaining: Long = 0
    private lateinit var firstTeamName: String
    private lateinit var secondTeamName: String
    private var firstTeamGoals: Int? = null
    private var secondTeamGoals: Int? = null
    private var matchActive by Delegates.notNull<Boolean>()
    private var matchIsLive by Delegates.notNull<Int>()
    private lateinit var countUpTimer: CountUpTimer
    private var eventsList = listOf("Гол", "Желтая карточка", "Красная карточка")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchProgressFragmentBinding.inflate(inflater, container, false)
        firstTeamName = arguments?.getString("team1name")!!
        secondTeamName = arguments?.getString("team2name")!!
        firstTeamGoals = arguments?.getInt("team1goals")
        secondTeamGoals = arguments?.getInt("team2goals")
        matchActive = arguments?.getBoolean("active")!!
        matchIsLive = arguments?.getInt("isLive")!!
        viewModel = ViewModelProvider(this)[MatchProgressViewModel::class.java]
        return binding.root
    }



    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        firstTeamNameEditText.setText(firstTeamName)
        secondTeamNameEditText.setText(secondTeamName)
        firstTeamScoreTextView.text = firstTeamGoals.toString()
        secondTeamScoreTextView.text = secondTeamGoals.toString()
        firstTeamFirstTimeFoulsTextView.text = firstTeamName
        secondTeamFirstTimeFoulsTextView.text = secondTeamName
        firstTeamSecondTimeFoulsTextView.text = firstTeamName
        secondTeamSecondTimeFoulsTextView.text = secondTeamName

        startCardView.setOnClickListener {
            if (!isTimerRunning) {
                startTimer()
            }
        }

        stopCardView.setOnClickListener {
            stopTimer()
        }

        finishCardView.setOnClickListener {
            stopTimer()
            val customView = layoutInflater.inflate(
                R.layout.match_progress_end_snackbar,
                null
            )
            val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
            snackBar.view.setBackgroundColor(Color.TRANSPARENT)

            val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
            snackBar.view.layoutParams = params
            val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

            val button: Button = customView!!.findViewById(R.id.button)
            button.setOnClickListener {
                findNavController().navigate(R.id.action_matchProgressFragment_to_matchRegisterFragment)
            }
            snackBarLayout.addView(customView, 0)
            snackBar.show()
        }

        leaveCardView.setOnClickListener {
            if(isTimerRunning) {
                val customView = layoutInflater.inflate(
                    R.layout.exit_live_snackbar_layout,
                    null
                )
                val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
                snackBar.view.setBackgroundColor(Color.TRANSPARENT)

                val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
                snackBar.view.layoutParams = params
                val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

                snackBarLayout.addView(customView, 0)
                snackBar.show()
            } else {
                findNavController().navigate(R.id.action_matchProgressFragment_to_matchRegisterFragment)
            }
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

        redCardSwitch.setOnCheckedChangeListener { _, isChecked ->
            eventTimeRedCardEditText.isEnabled = !isChecked
            if (isChecked){
                eventTimeRedCardPlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeRedCardMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeRedCardEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border))
            } else {
                eventTimeRedCardPlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeRedCardMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeRedCardEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            }
        }

        goalSwitch.setOnCheckedChangeListener{_, isChecked ->
            eventTimeGoalEditText.isEnabled = !isChecked
            if (isChecked){
                eventTimeGoalPlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeGoalMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeGoalEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border))
            } else {
                eventTimeGoalPlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeGoalMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeGoalEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
            }
        }

        yellowCardSwitch.setOnCheckedChangeListener { _, isChecked ->
            eventTimeEditText.isEnabled = !isChecked
            if (isChecked){
                eventTimePlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border2))
                eventTimeEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.border))
            } else {
                eventTimePlusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeMinusImageButton.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                eventTimeEditText.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
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
                val selectedFruit = eventsList[position]
                when (selectedFruit){
                    "Гол" -> {
                        goalHiddenConstraintLayout.visibility = View.VISIBLE
                        yellowCardHiddenConstraintLayout.visibility = View.GONE
                        redCardHiddenConstraintLayout.visibility = View.GONE
                    }
                    "Желтая карточка" -> {
                        goalHiddenConstraintLayout.visibility = View.GONE
                        yellowCardHiddenConstraintLayout.visibility = View.VISIBLE
                        redCardHiddenConstraintLayout.visibility = View.GONE
                    }
                    "Красная карточка" -> {
                        goalHiddenConstraintLayout.visibility = View.GONE
                        yellowCardHiddenConstraintLayout.visibility = View.GONE
                        redCardHiddenConstraintLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }
        }
    }



    private fun startTimer() = with(binding) {
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

        startCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
        startTimeConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        startTimerTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
        startImageView.setImageResource(R.drawable.arrow_start_icon_unavailable)

        stopConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
        stopCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
        stopTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        stopButtonImageView.setImageResource(R.drawable.stop_available_icon)
    }



    private fun stopTimer() = with(binding) {
        if (isTimerRunning) {
            countUpTimer.cancel()
            isTimerRunning = false
            secondsRemaining = 0
            updateTimerText()
        }
        startCardView.strokeColor = ContextCompat.getColor(requireContext(), blue)
        startTimeConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), blue))
        startTimerTextView.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        startImageView.setImageResource(R.drawable.arrow_start_icon)

        stopConstraintLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
        stopCardView.strokeColor = ContextCompat.getColor(requireContext(), R.color.unavailableText)
        stopTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.unavailableText))
        stopButtonImageView.setImageResource(R.drawable.stop_unavailable_icon)
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