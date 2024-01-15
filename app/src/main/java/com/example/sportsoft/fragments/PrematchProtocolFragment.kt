package com.example.sportsoft.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsoft.adapters.TeamPlayersPrematchProtocolAdapter
import com.example.sportsoft.models.PlayerModel
import com.example.sportsoft.R
import com.example.sportsoft.databinding.PrematchProtocolFragmentBinding
import com.example.sportsoft.viewModels.PrematchProtocolViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlin.properties.Delegates

class PrematchProtocolFragment : Fragment(R.layout.prematch_protocol_fragment) {
    private var _binding : PrematchProtocolFragmentBinding? = null
    private val binding get() = _binding!!
    private val firstTeamAdapter = TeamPlayersPrematchProtocolAdapter()
    private val secondTeamAdapter = TeamPlayersPrematchProtocolAdapter()
    private lateinit var firstTeamName: String
    private lateinit var secondTeamName: String
    private var firstTeamGoals: Int? = null
    private var secondTeamGoals: Int? = null
    private var matchActive by Delegates.notNull<Boolean>()
    private var matchIsLive by Delegates.notNull<Int>()
    private lateinit var viewModel: PrematchProtocolViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PrematchProtocolFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[PrematchProtocolViewModel::class.java]
        if (viewModel.firstTeamName.value == null) {
            firstTeamName = arguments?.getString("team1name") ?: ""
            secondTeamName = arguments?.getString("team2name") ?: ""
            firstTeamGoals = arguments?.getInt("team1goals", 0)
            secondTeamGoals = arguments?.getInt("team2goals", 0)
            matchActive = arguments?.getBoolean("active", false)!!
            matchIsLive = arguments?.getInt("isLive", 0)!!

            viewModel.updateValues(
                firstTeamName,
                secondTeamName,
                firstTeamGoals,
                secondTeamGoals,
                matchActive,
                matchIsLive
            )
        } else {
            firstTeamName = viewModel.firstTeamName.value ?: ""
            secondTeamName = viewModel.secondTeamName.value ?: ""
            firstTeamGoals = viewModel.firstTeamGoals.value ?: 0
            secondTeamGoals = viewModel.secondTeamGoals.value ?: 0
            matchActive = viewModel.matchActive.value ?: false
            matchIsLive = viewModel.matchIsLive.value ?: 0
        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        viewModel.firstTeamName.observe(viewLifecycleOwner) { value ->
            firstTeamNameEditText.setText(value)
        }

        viewModel.secondTeamName.observe(viewLifecycleOwner){ value ->
            secondTeamNameEditText.setText(value)
        }

        viewModel.firstTeamGoals.observe(viewLifecycleOwner){ value ->
            firstTeamScoreTextView.setText(value.toString())
        }

        viewModel.secondTeamGoals.observe(viewLifecycleOwner){ value ->
            secondTeamScoreTextView.setText(value.toString())
        }

        firstTeamPlayerTextView.text = resources.getString(R.string.PlayerMatch, firstTeamName)
        secondTeamPlayerTextView.text = resources.getString(R.string.PlayerMatch, secondTeamName)
        firstTeamCompositionTextView.text = resources.getString(R.string.TeamСomposition, firstTeamName)
        secondTeamCompositionTextView.text = resources.getString(R.string.TeamСomposition, secondTeamName)

        refereesMatchConstraintLayout.setOnClickListener {
            toggleVisibility(hiddenRefereesConstraintLayout, arrowDownImageView, separator3)
        }

        compositionFirstTeamConstraintLayout.setOnClickListener {
            toggleVisibility(hiddenCompositionFirstTeamConstraintLayout, arrowDownCompositionImageView, separator5)
        }

        compositionSecondTeamConstraintLayout.setOnClickListener {
            toggleVisibility(hiddenCompositionSecondTeamConstraintLayout, arrowDownCompositionSecondTeamImageView, separator6)
        }

        firstTeamScoreCountPlusImageView.setOnClickListener {
            val score = firstTeamScoreTextView.text.toString().toInt()
            firstTeamScoreTextView.setText(viewModel.changeScore(score, true).toString())
            viewModel.updateFirstTeamScoreCountPlus(score + 1)
        }

        firstTeamScoreCountMinusImageView.setOnClickListener {
            val score = firstTeamScoreTextView.text.toString().toInt()
            firstTeamScoreTextView.setText(viewModel.changeScore(score, false).toString())
            if (score != 0){
                viewModel.updateFirstTeamScoreCountPlus(score - 1)
            }
        }

        secondTeamScoreCountPlusImageView.setOnClickListener {
            val score = secondTeamScoreTextView.text.toString().toInt()
            secondTeamScoreTextView.setText(viewModel.changeScore(score, true).toString())
            viewModel.updateSecondTeamScoreCountPlus(score + 1)
        }

        secondTeamScoreCountMinusImageView.setOnClickListener {
            val score = secondTeamScoreTextView.text.toString().toInt()
            secondTeamScoreTextView.setText(viewModel.changeScore(score, false).toString())
            if (score != 0){
                viewModel.updateSecondTeamScoreCountPlus(score - 1)
            }
        }

        menuImageButton.setOnClickListener { view ->
            showPopupMenu(view)
        }

        saveAndStartButton.setOnClickListener {
            viewModel.updateValues(
                firstTeamName,
                secondTeamName,
                firstTeamGoals,
                secondTeamGoals,
                matchActive,
                matchIsLive
            )
            val bundle = createBundle(
                viewModel.firstTeamName.value ?: "",
                viewModel.secondTeamName.value ?: "",
                viewModel.firstTeamGoals.value,
                viewModel.secondTeamGoals.value,
                viewModel.matchActive.value ?: false,
                viewModel.matchIsLive.value ?: 0
            )
            findNavController().navigate(R.id.action_prematchProtocolFragment_to_matchProgressFragment, bundle)
        }

        saveAllButton.setOnClickListener {
            showSnackBar()
        }

        firstTeamAdapter.setList(
            listOf(
                PlayerModel(
                    1,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    2,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    3,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    4,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    5,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    6,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    7,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    8,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    9,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    10,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    11,
                    "https://premierliga.ru/netcat_files/sportbase/playerApplication/42962/42081_1665558190699_cropped.jpg",
                    "И. В. Акинфеев",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                )
            )
        )

        secondTeamAdapter.setList(
            listOf(
                PlayerModel(
                    1,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    2,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    3,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    4,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    5,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    6,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    7,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    8,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    9,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    10,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                ),
                PlayerModel(
                    11,
                    "https://stuki-druki.com/biofoto/Andrey-Arshavin-01.jpg",
                    "А. С. Аршавин",
                    param1 = false,
                    param2 = false,
                    captain = false,
                    goalkeeper = false
                )
            )
        )

        val refereeNameList = listOf("Николаев А.В.", "Николаев А.В.", "Николаев А.В.", "Николаев А.В.")
        val refereePositionList = listOf("Главный судья", "Помощник главного судьи", "Резервный судья", "Резервный помощник судьи", "Дополнительный помощник")

        firstTeamCompositionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        firstTeamCompositionRecyclerView.adapter = firstTeamAdapter

        secondTeamCompositionRecyclerView.layoutManager= LinearLayoutManager(requireContext())
        secondTeamCompositionRecyclerView.adapter = secondTeamAdapter

        val firstNameNamesList: List<String> = firstTeamAdapter.getList().map { player -> player.name }
        val secondNameNamesList: List<String> = secondTeamAdapter.getList().map { player -> player.name }

        val firstTeamPlayerSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, firstNameNamesList)
        firstTeamPlayerSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        firstTeamPlayerSpinner.adapter = firstTeamPlayerSpinnerAdapter

        val secondTeamPlayerSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, secondNameNamesList)
        secondTeamPlayerSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        secondTeamPlayerSpinner.adapter = secondTeamPlayerSpinnerAdapter

        val refereeNameSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, refereeNameList)
        refereeNameSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        refereeSpinner.adapter = refereeNameSpinnerAdapter

        val refereePositionSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, refereePositionList)
        refereePositionSpinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item)
        refereePositionSpinner.adapter = refereePositionSpinnerAdapter
    }



    private fun createBundle(
        team1name: String,
        team2name: String,
        team1goals: Int?,
        team2goals: Int?,
        active: Boolean,
        isLive: Int
    ): Bundle {
        val bundle = Bundle()
        bundle.putString("team1name", team1name)
        bundle.putString("team2name", team2name)
        if (team1goals != null) {
            bundle.putInt("team1goals", team1goals)
        }
        if (team2goals != null) {
            bundle.putInt("team2goals", team2goals)
        }
        bundle.putBoolean("active", active)
        bundle.putInt("isLive", isLive)
        return bundle
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    @SuppressLint("RestrictedApi", "InflateParams")
    fun showSnackBar(){
        val customView = layoutInflater.inflate(
            R.layout.prematch_protocol_snackbar_layout,
            null
        )
        val snackBar = Snackbar.make(binding.root, "", Snackbar.LENGTH_SHORT)
        snackBar.view.setBackgroundColor(Color.TRANSPARENT)

        val params = snackBar.view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snackBar.view.layoutParams = params
        val snackBarLayout = snackBar.view as SnackbarLayout
        snackBarLayout.addView(customView, 0)
        snackBar.show()
    }



    private fun showPopupMenu(view: View){
        val popupMenu = PopupMenu(requireContext(), view, Gravity.END, 0, R.style.PopupMenuStyle)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.matchRegister -> {
                    findNavController().navigate(R.id.action_prematchProtocolFragment_to_matchRegisterFragment)
                    true
                }
                R.id.exit -> {
                    findNavController().navigate(R.id.action_prematchProtocolFragment_to_authorizationFragment)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }



    private fun toggleVisibility(hiddenLayout: View, arrowImageView: ImageView, separatorView: View) = with(binding) {
        if (hiddenLayout.visibility == View.VISIBLE) {
            TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
            hiddenLayout.visibility = View.GONE
            arrowImageView.setImageResource(R.drawable.arrow_down_icon)
            separatorView.visibility = View.VISIBLE
        } else {
            TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
            hiddenLayout.visibility = View.VISIBLE
            arrowImageView.setImageResource(R.drawable.arrow_up_icon)
            separatorView.visibility = View.GONE
        }
    }
}