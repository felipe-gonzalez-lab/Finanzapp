package com.example.finanzapp.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovimientoApiService {

    @GET("api/movimientos")
    suspend fun listarMovimientos(): Response<List<MovimientoApi>>

    @GET("api/movimientos/{id}")
    suspend fun buscarMovimientoPorId(
        @Path("id") id: Long
    ): Response<MovimientoApi>

    @POST("api/movimientos")
    suspend fun crearMovimiento(
        @Body movimiento: MovimientoApi
    ): Response<MovimientoApi>

    @PUT("api/movimientos/{id}")
    suspend fun actualizarMovimiento(
        @Path("id") id: Long,
        @Body movimiento: MovimientoApi
    ): Response<MovimientoApi>

    @DELETE("api/movimientos/{id}")
    suspend fun eliminarMovimiento(
        @Path("id") id: Long
    ): Response<Unit>
}