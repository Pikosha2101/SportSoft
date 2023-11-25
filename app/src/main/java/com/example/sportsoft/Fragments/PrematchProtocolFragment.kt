package com.example.sportsoft.Fragments

import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            } else {
                TransitionManager.beginDelayedTransition(baseCardview, AutoTransition())
                hiddenRefereesConstraintLayout.visibility = View.VISIBLE
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}