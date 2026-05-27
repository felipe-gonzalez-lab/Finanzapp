package com.example.finanzapp.data.remote

import com.example.finanzapp.data.remote.dto.IndicadoresResponse
import retrofit2.http.GET

interface IndicadoresApi {

    @GET("api")
    suspend fun obtenerIndicadores(): IndicadoresResponse
}