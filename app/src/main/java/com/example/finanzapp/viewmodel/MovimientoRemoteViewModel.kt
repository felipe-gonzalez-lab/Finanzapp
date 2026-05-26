package com.example.finanzapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.data.remote.MovimientoApi
import com.example.finanzapp.repository.MovimientoRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovimientoRemoteViewModel : ViewModel() {

    private val repository = MovimientoRemoteRepository()

    private val _movimientosBackend = MutableStateFlow<List<MovimientoApi>>(emptyList())
    val movimientosBackend: StateFlow<List<MovimientoApi>> = _movimientosBackend

    private val _mensajeBackend = MutableStateFlow("")
    val mensajeBackend: StateFlow<String> = _mensajeBackend

    fun cargarMovimientosBackend() {
        viewModelScope.launch {
            try {
                _mensajeBackend.value = "Cargando movimientos desde backend..."
                _movimientosBackend.value = repository.listarMovimientos()
                _mensajeBackend.value = "Movimientos cargados correctamente"
            } catch (e: Exception) {
                _mensajeBackend.value = "Error al conectar con backend: ${e.message}"
            }
        }
    }

    fun crearMovimientoBackend() {
        viewModelScope.launch {
            try {
                val movimiento = MovimientoApi(
                    tipo = "Gasto",
                    categoria = "Alimentación",
                    monto = 15000.0,
                    fecha = "2026-05-25",
                    descripcion = "Movimiento creado desde Android"
                )

                repository.crearMovimiento(movimiento)
                _mensajeBackend.value = "Movimiento creado en backend"
                cargarMovimientosBackend()
            } catch (e: Exception) {
                _mensajeBackend.value = "Error al crear movimiento: ${e.message}"
            }
        }
    }

    fun actualizarMovimientoBackend(id: Long) {
        viewModelScope.launch {
            try {
                val movimientoActualizado = MovimientoApi(
                    id = id,
                    tipo = "Gasto",
                    categoria = "Servicios básicos",
                    monto = 30000.0,
                    fecha = "2026-05-26",
                    descripcion = "Movimiento actualizado desde Android"
                )

                repository.actualizarMovimiento(id, movimientoActualizado)
                _mensajeBackend.value = "Movimiento actualizado en backend"
                cargarMovimientosBackend()
            } catch (e: Exception) {
                _mensajeBackend.value = "Error al actualizar movimiento: ${e.message}"
            }
        }
    }

    fun eliminarMovimientoBackend(id: Long) {
        viewModelScope.launch {
            try {
                repository.eliminarMovimiento(id)
                _mensajeBackend.value = "Movimiento eliminado del backend"
                cargarMovimientosBackend()
            } catch (e: Exception) {
                _mensajeBackend.value = "Error al eliminar movimiento: ${e.message}"
            }
        }
    }
}