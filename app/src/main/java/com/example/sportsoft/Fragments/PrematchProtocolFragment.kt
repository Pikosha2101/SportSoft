package com.example.sportsoft.Fragments

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sportsoft.R
import com.example.sportsoft.databinding.PrematchProtocolFragmentBinding

class PrematchProtocolFragment : Fragment(R.layout.prematch_protocol_fragment) {
    private var _binding : PrematchProtocolFragmentBinding? = null
    private val binding get() = _binding!!



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
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}