package com.example.sportsoft.Fragments

import android.os.Bundle
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

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}