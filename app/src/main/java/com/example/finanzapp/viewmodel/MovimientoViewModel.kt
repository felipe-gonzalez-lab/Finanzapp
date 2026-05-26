package com.example.finanzapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.data.local.entity.Movimiento
import com.example.finanzapp.data.remote.MovimientoApi
import com.example.finanzapp.repository.MovimientoRepository
import com.example.finanzapp.repository.MovimientoRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovimientoViewModel(
    private val repository: MovimientoRepository
) : ViewModel() {

    private val remoteRepository = MovimientoRemoteRepository()

    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val movimientos: StateFlow<List<Movimiento>> = _movimientos

    private val _totalConsulta = MutableStateFlow<Double?>(null)
    val totalConsulta: StateFlow<Double?> = _totalConsulta

    private val _mensajeBackend = MutableStateFlow("")
    val mensajeBackend: StateFlow<String> = _mensajeBackend

    fun cargarMovimientos() {
        viewModelScope.launch {
            _movimientos.value = repository.obtenerTodos()
        }
    }

    fun insertarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.insertar(movimiento)
            enviarMovimientoAlBackend(movimiento)
            cargarMovimientos()
        }
    }

    fun actualizarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.actualizar(movimiento)
            cargarMovimientos()
        }
    }

    fun eliminarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.eliminar(movimiento)
            cargarMovimientos()
        }
    }

    fun consultarTotal(categoria: String, mes: String) {
        viewModelScope.launch {
            _totalConsulta.value = repository.totalPorCategoriaMes(categoria, "$mes%")
        }
    }

    fun consultarTotalPorTipo(tipo: String, mes: String) {
        viewModelScope.launch {
            _totalConsulta.value = repository.totalPorTipoMes(tipo, "$mes%")
        }
    }

    private suspend fun enviarMovimientoAlBackend(movimiento: Movimiento) {
        try {
            val movimientoApi = MovimientoApi(
                tipo = movimiento.tipo,
                categoria = movimiento.categoria,
                monto = movimiento.monto,
                fecha = movimiento.fecha,
                descripcion = movimiento.descripcion
            )

            remoteRepository.crearMovimiento(movimientoApi)
            _mensajeBackend.value = "Movimiento enviado al backend correctamente"
        } catch (e: Exception) {
            _mensajeBackend.value = "El movimiento se guardó localmente, pero no se pudo enviar al backend"
        }
    }
}