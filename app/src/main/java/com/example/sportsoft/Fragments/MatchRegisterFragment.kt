package com.example.sportsoft.Fragments

import android.app.DatePickerDialog
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
import com.example.sportsoft.API.MatchHostnameVerifier
import com.example.sportsoft.API.MatchInterceptor
import com.example.sportsoft.API.ApiModels.MatchResponse
import com.example.sportsoft.API.ApiModels.MatchesModel
import com.example.sportsoft.API.ApiService
import com.example.sportsoft.API.Server
import com.example.sportsoft.Adapters.MatchRecyclerAdapter
import com.example.sportsoft.Listener
import com.example.sportsoft.Models.MatchModel
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
import java.util.Calendar
import java.util.Locale

class MatchRegisterFragment : Fragment(R.layout.match_register_fragment), Listener<MatchModel>{
    private var _binding : MatchRegisterFragmentBinding? = null
    private val binding get() = _binding!!
    private val adapter = MatchRecyclerAdapter(this)
    private var selectedFromDate: Calendar? = null
    private var selectedToDate: Calendar? = null
    private lateinit var userToken: String
    private val PREFS_NAME = "SharedPreferences"
    private val USER_TOKEN = "Token"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MatchRegisterFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, 0)
        userToken = sharedPreferences.getString(USER_TOKEN, "").toString()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = createOkHttpClient(interceptor, userToken)
        val retrofit = createRetrofitInstance(client)
        val apiService = retrofit.create(ApiService::class.java)
        val request = MatchesModel(Server().getToken(), userToken)
        val call = apiService.getMatches(request)
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

        teamSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по тиме", Toast.LENGTH_SHORT).show()
        }

        scoreSortConstraint.setOnClickListener {
            Toast.makeText(requireContext(), "Сортировка по счёту", Toast.LENGTH_SHORT).show()
        }

        menuImageButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it, Gravity.END, 0, R.style.PopupMenuStyle)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    R.id.matchRegister -> {
                        /*findNavController().navigate(R.id.)*/
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

        adapter.setList(
            listOf(
                MatchModel(
                    "22.11.23",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "23.11.23",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "24.11.23",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "25.11.23",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                ),
                MatchModel(
                    "26.11.23",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "27.11.23",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "28.11.23",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "29.11.23",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                ),
                MatchModel(
                    "26.11.23",
                    "ФК Спартак",
                    "ФК Краснодар",
                    2,
                    1
                ),
                MatchModel(
                    "27.11.23",
                    "ФК Зенит",
                    "ФК Тосно",
                    0,
                    1
                ),
                MatchModel(
                    "28.11.23",
                    "ФК Крылья Советов",
                    "ФК Локомотив",
                    2,
                    2
                ),
                MatchModel(
                    "29.11.23",
                    "ФК Урал",
                    "ФК Спартак",
                    0,
                    0
                )
            )
        )
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    override fun onClickEdit(param: MatchModel) {
        findNavController().navigate(R.id.action_matchRegisterFragment_to_prematchProtocolFragment)
    }



    override fun onClickStart(param: MatchModel) {
        findNavController().navigate(R.id.action_matchRegisterFragment_to_matchProgressFragment)
    }



    private fun showDatePickerFromDialog() {
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
                binding.dateFromEditText.text = formattedDate.format(selectedFromDate!!.time)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis

        datePickerDialog.show()
    }



    private fun showDatePickerToDialog() {
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
                binding.dateToEditText.text = formattedDate.format(selectedToDate!!.time)
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
                        Toast.makeText(requireContext(), "Корректно получаются данные", Toast.LENGTH_SHORT).show()
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