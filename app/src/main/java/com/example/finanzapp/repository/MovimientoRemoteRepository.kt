package com.example.finanzapp.repository

import com.example.finanzapp.data.remote.MovimientoApi
import com.example.finanzapp.data.remote.RetrofitClient

class MovimientoRemoteRepository {

    private val apiService = RetrofitClient.movimientoApiService

    suspend fun listarMovimientos(): List<MovimientoApi> {
        val respuesta = apiService.listarMovimientos()

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: emptyList()
        } else {
            throw Exception("Error al listar movimientos")
        }
    }

    suspend fun crearMovimiento(movimiento: MovimientoApi): MovimientoApi {
        val respuesta = apiService.crearMovimiento(movimiento)

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: throw Exception("Respuesta vacía del servidor")
        } else {
            throw Exception("Error al crear movimiento")
        }
    }

    suspend fun actualizarMovimiento(id: Long, movimiento: MovimientoApi): MovimientoApi {
        val respuesta = apiService.actualizarMovimiento(id, movimiento)

        if (respuesta.isSuccessful) {
            return respuesta.body() ?: throw Exception("Respuesta vacía del servidor")
        } else {
            throw Exception("Error al actualizar movimiento")
        }
    }

    suspend fun eliminarMovimiento(id: Long) {
        val respuesta = apiService.eliminarMovimiento(id)

        if (!respuesta.isSuccessful) {
            throw Exception("Error al eliminar movimiento")
        }
    }
}