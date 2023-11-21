package com.example.sportsoft.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sportsoft.R
import com.example.sportsoft.databinding.AuthorizationFragmentBinding

class AuthorizationFragment : Fragment(R.layout.authorization_fragment) {
    private var _binding : AuthorizationFragmentBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthorizationFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        signinButton.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_matchRegisterFragment)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}