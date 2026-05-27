package com.example.finanzapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL_BACKEND = "http://10.0.2.2:8080/"
    private const val BASE_URL_INDICADORES = "https://mindicador.cl/"

    val movimientoApiService: MovimientoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_BACKEND)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovimientoApiService::class.java)
    }

    val indicadoresApi: IndicadoresApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_INDICADORES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IndicadoresApi::class.java)
    }
}