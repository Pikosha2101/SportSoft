package com.example.sportsoft.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportsoft.api.AuthHostnameVerifier
import com.example.sportsoft.api.AuthInterceptor
import com.example.sportsoft.api.Server
import com.example.sportsoft.api.apiModels.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Base64

class AuthorizationViewModel: ViewModel() {
    // LiveData для обработки состояния запроса
    val loginResponseLiveData: MutableLiveData<Response<LoginResponse?>> = MutableLiveData()
    //возможные ошибки
    val errorLiveData: MutableLiveData<String> = MutableLiveData()


    fun base64Encoder(login: String, password: String): String{
        val credentials = "$login:$password"
        return Base64.getEncoder().encodeToString(credentials.toByteArray())
    }


    fun createOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        authData: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor(authData))
            .hostnameVerifier(AuthHostnameVerifier())
            .build()
    }


    fun createRetrofitInstance(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Server().getServer())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



    fun serverRequest(call: Call<LoginResponse>){
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse?>) {
                if (response.code() == 200) {
                    // Обновление LiveData в случае успешного запроса
                    //loginResponseLiveData.value = LoginResponseState.Success(apiResponse.token)
                    loginResponseLiveData.postValue(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Обновление LiveData в случае неудачного запроса
                //loginResponseLiveData.value = LoginResponseState.Failure(R.string.TheRequestFailed)
                errorLiveData.postValue(t.message)
            }
        })
    }
}
