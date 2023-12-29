package com.example.sportsoft.Fragments

import android.os.Bundle
import android.text.Editable
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsoft.Adapters.TeamPlayersPrematchProtocolAdapter
import com.example.sportsoft.Models.PlayerModel
import com.example.sportsoft.R
import com.example.sportsoft.databinding.PrematchProtocolFragmentBinding

class PrematchProtocolFragment : Fragment(R.layout.prematch_protocol_fragment) {
    private var _binding : PrematchProtocolFragmentBinding? = null
    private val binding get() = _binding!!
    private val firstTeamAdapter = TeamPlayersPrematchProtocolAdapter()
    private val secondTeamAdapter = TeamPlayersPrematchProtocolAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PrematchProtocolFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        refereesMatchConstraintLayout.setOnClickListener {
            if (hiddenRefereesConstraintLayout.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
                hiddenRefereesConstraintLayout.visibility = View.GONE
                arrowDownImageView.setImageResource(R.drawable.arrow_down_icon)
                separator3.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
                hiddenRefereesConstraintLayout.visibility = View.VISIBLE
                arrowDownImageView.setImageResource(R.drawable.arrow_up_icon)
                separator3.visibility = View.GONE
            }
        }

        compositionFirstTeamConstraintLayout.setOnClickListener {
            if (hiddenCompositionFirstTeamConstraintLayout.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(compositionFirstTeamCardView, AutoTransition())
                hiddenCompositionFirstTeamConstraintLayout.visibility = View.GONE
                arrowDownCompositionImageView.setImageResource(R.drawable.arrow_down_icon)
                separator5.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(compositionFirstTeamCardView, AutoTransition())
                hiddenCompositionFirstTeamConstraintLayout.visibility = View.VISIBLE
                arrowDownCompositionImageView.setImageResource(R.drawable.arrow_up_icon)
                separator5.visibility = View.GONE
            }
        }

        compositionSecondTeamConstraintLayout.setOnClickListener {
            if (hiddenCompositionSecondTeamConstraintLayout.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(compositionSecondTeamCardView, AutoTransition())
                hiddenCompositionSecondTeamConstraintLayout.visibility = View.GONE
                arrowDownCompositionSecondTeamImageView.setImageResource(R.drawable.arrow_down_icon)
                separator6.visibility = View.VISIBLE
            } else {
                TransitionManager.beginDelayedTransition(compositionSecondTeamCardView, AutoTransition())
                hiddenCompositionSecondTeamConstraintLayout.visibility = View.VISIBLE
                arrowDownCompositionSecondTeamImageView.setImageResource(R.drawable.arrow_up_icon)
                separator6.visibility = View.GONE
            }
        }



        firstTeamScoreCountPlusImageView.setOnClickListener {
            val score = firstTeamScoreTextView.text.toString().toInt()
            firstTeamScoreTextView.text = Editable.Factory.getInstance().newEditable((score + 1).toString())
        }

        firstTeamScoreCountMinusImageView.setOnClickListener {
            val score = firstTeamScoreTextView.text.toString().toInt()
            if (score != 0){
                firstTeamScoreTextView.text = Editable.Factory.getInstance().newEditable((score - 1).toString())
            }
        }

        secondTeamScoreCountPlusImageView.setOnClickListener {
            val score = secondTeamScoreTextView.text.toString().toInt()
            secondTeamScoreTextView.text = Editable.Factory.getInstance().newEditable((score + 1).toString())
        }

        secondTeamScoreCountMinusImageView.setOnClickListener {
            val score = secondTeamScoreTextView.text.toString().toInt()
            if (score != 0){
                secondTeamScoreTextView.text = Editable.Factory.getInstance().newEditable((score - 1).toString())
            }
        }

        menuImageButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it, Gravity.END, 0, R.style.PopupMenuStyle)
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


        saveAndStartButton.setOnClickListener {
            findNavController().navigate(R.id.action_prematchProtocolFragment_to_matchProgressFragment)
        }

        saveAllButton.setOnClickListener {
            findNavController().navigate(R.id.action_prematchProtocolFragment_to_matchRegisterFragment)
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



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}