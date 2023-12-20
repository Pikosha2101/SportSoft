package com.example.sportsoft.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sportsoft.API.ApiService
import com.example.sportsoft.API.LoginRequest
import com.example.sportsoft.API.LoginResponse
import com.example.sportsoft.API.MyInterceptor
import com.example.sportsoft.API.Server
import com.example.sportsoft.API.MyHostnameVerifier
import com.example.sportsoft.R
import com.example.sportsoft.databinding.AuthorizationFragmentBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

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
            if(loginEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                inputOperations()
            } else {
                Toast.makeText(requireContext(), R.string.EnterAllDetails, Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun inputOperations() = with(binding){
        val username = loginEditText.text.toString()
        val password = passwordEditText.text.toString()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val authData = base64Encoder(username, password)
        val client = createOkHttpClient(interceptor, authData)
        val retrofit = createRetrofitInstance(client)
        val apiService = retrofit.create(ApiService::class.java)
        val request = LoginRequest(Server().getToken(), authData)
        val call = apiService.login(request)
        serverRequest(call)
    }



    private fun base64Encoder(login: String, password: String): String{
        val credentials = "$login:$password"
        return Base64.getEncoder().encodeToString(credentials.toByteArray())
    }



    private fun serverRequest(call: Call<LoginResponse>){
        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                if(response.isSuccessful){
                    val apiResponse = response.body()
                    if (apiResponse != null && apiResponse.success){
                        findNavController().navigate(R.id.action_authorizationFragment_to_matchRegisterFragment)
                    } else {
                        Toast.makeText(requireContext(), R.string.IncorrectData, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.NetworkError, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(requireContext(), R.string.TheRequestFailed, Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun createRetrofitInstance(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Server().getServer())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



    private fun createOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authData: String): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(MyInterceptor(authData))
            .hostnameVerifier(MyHostnameVerifier())
            .build()
    }
}