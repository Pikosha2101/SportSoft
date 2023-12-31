package com.example.sportsoft.fragments

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sportsoft.api.apiModels.MatchInfo
import com.example.sportsoft.api.MatchHostnameVerifier
import com.example.sportsoft.api.MatchInterceptor
import com.example.sportsoft.api.apiModels.MatchResponse
import com.example.sportsoft.api.ApiService
import com.example.sportsoft.api.Server
import com.example.sportsoft.adapters.MatchRecyclerAdapter
import com.example.sportsoft.Listener
import com.example.sportsoft.R
import com.example.sportsoft.databinding.MatchRegisterFragmentBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MatchRegisterFragment : Fragment(R.layout.match_register_fragment), Listener<MatchInfo>{
    private var _binding : MatchRegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MatchRecyclerAdapter(this)
    private var selectedFromDate: Calendar? = null
    private var selectedToDate: Calendar? = null
    private lateinit var userToken: String
    private val PREFS_NAME = "TokenSharedPreferences"
    private val USER_TOKEN = "Token"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchRegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)
        val dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
        val currentDate = LocalDate.now()
        val formattedDate = dateFormat.format(currentDate)
        val previousDate = currentDate.minusDays(7)
        val formattedPreviousDate = previousDate.format(dateFormat)
        dateToEditText.text = formattedDate
        dateFromEditText.text = formattedPreviousDate
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, 0)
        userToken = sharedPreferences.getString(USER_TOKEN, "").toString()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = createOkHttpClient(interceptor, userToken)
        val retrofit = createRetrofitInstance(client)
        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getMatches()
        serverRequest(call)



        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        datePickerCardViewFrom.setOnClickListener {
            showDatePickerFromDialog()
        }
        
        datePickerCardViewTo.setOnClickListener {
            showDatePickerToDialog()
        }

        dateSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по дате", Toast.LENGTH_SHORT).show()
        }

        menuImageButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it, Gravity.END, 0, R.style.PopupMenuStyle)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.matchRegister -> {
                        true
                    }
                    R.id.exit -> {
                        findNavController().navigate(R.id.action_matchRegisterFragment_to_authorizationFragment)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    override fun onClickEdit(param: MatchInfo) {
        val bundle = createBundle(
            param.team1_shortname,
            param.team2_shortname,
            param.gf,
            param.ga,
            param.active,
            param.is_live
        )
        findNavController().navigate(R.id.action_matchRegisterFragment_to_prematchProtocolFragment, bundle)
    }



    override fun onClickStart(param: MatchInfo) {
        val bundle = createBundle(
            param.team1_shortname,
            param.team2_shortname,
            param.gf,
            param.ga,
            param.active,
            param.is_live
        )
        findNavController().navigate(R.id.action_matchRegisterFragment_to_matchProgressFragment, bundle)
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


    private fun showDatePickerFromDialog() = with(binding) {
        val currentDate = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialogStyle,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                selectedFromDate = if (selectedDate.after(currentDate)) {
                    currentDate
                } else {
                    selectedDate
                }

                val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                dateFromEditText.text = formattedDate.format(selectedFromDate!!.time)
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = createOkHttpClient(interceptor, userToken)
                val retrofit = createRetrofitInstance(client)
                val apiService = retrofit.create(ApiService::class.java)
                val call = apiService.getMatches(dateFromEditText.text.toString(), dateToEditText.text.toString())
                serverRequest(call)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis
        datePickerDialog.show()
    }



    private fun showDatePickerToDialog() = with(binding) {
        val currentDate = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialogStyle,
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                selectedToDate = if (selectedDate.before(selectedFromDate)) {
                    selectedFromDate
                } else {
                    selectedDate
                }

                val formattedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                dateToEditText.text = formattedDate.format(selectedToDate!!.time)

                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = createOkHttpClient(interceptor, userToken)
                val retrofit = createRetrofitInstance(client)
                val apiService = retrofit.create(ApiService::class.java)
                val call = apiService.getMatches(dateFromEditText.text.toString(), dateToEditText.text.toString())
                serverRequest(call)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis
        selectedFromDate?.let {
            datePickerDialog.datePicker.minDate = it.timeInMillis
        }
        datePickerDialog.show()
    }



    private fun createOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        accessToken: String): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(MatchInterceptor(accessToken))
            .hostnameVerifier(MatchHostnameVerifier())
            .build()
    }



    private fun createRetrofitInstance(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(Server().getServer())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



    private fun serverRequest(call: Call<MatchResponse>){
        call.enqueue(object : Callback<MatchResponse> {
            override fun onResponse(call: Call<MatchResponse>, response: Response<MatchResponse>) {
                if (response.isSuccessful) {
                    val matchResponse = response.body()
                    if (matchResponse?.success == true) {
                        adapter.setList(matchResponse.matches ?: emptyList())
                    } else {
                        Toast.makeText(requireContext(), "Даннные получены некорректно", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), R.string.NetworkError, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MatchResponse>, t: Throwable) {
                Toast.makeText(requireContext(), R.string.TheRequestFailed, Toast.LENGTH_SHORT).show()
            }
        })
    }
}