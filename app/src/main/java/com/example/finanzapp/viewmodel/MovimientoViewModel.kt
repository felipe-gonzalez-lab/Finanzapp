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
            val idLocal = repository.insertar(movimiento).toInt()

            enviarMovimientoAlBackend(
                movimiento = movimiento,
                idLocal = idLocal
            )

            cargarMovimientos()
        }
    }

    fun actualizarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            try {
                movimiento.backendId?.let { backendId ->
                    val movimientoApi = MovimientoApi(
                        id = backendId,
                        tipo = movimiento.tipo,
                        categoria = movimiento.categoria,
                        monto = movimiento.monto,
                        fecha = movimiento.fecha,
                        descripcion = movimiento.descripcion
                    )

                    remoteRepository.actualizarMovimiento(
                        id = backendId,
                        movimiento = movimientoApi
                    )
                }

                repository.actualizar(movimiento)
                _mensajeBackend.value = "Movimiento actualizado correctamente"
                cargarMovimientos()
            } catch (e: Exception) {
                _mensajeBackend.value = "No se pudo actualizar el movimiento en el backend"
            }
        }
    }

    fun eliminarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            try {
                movimiento.backendId?.let { backendId ->
                    remoteRepository.eliminarMovimiento(backendId)
                }

                repository.eliminar(movimiento)
                _mensajeBackend.value = "Movimiento eliminado correctamente"
                cargarMovimientos()
            } catch (e: Exception) {
                _mensajeBackend.value = "No se pudo eliminar el movimiento del backend"
            }
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

    private suspend fun enviarMovimientoAlBackend(
        movimiento: Movimiento,
        idLocal: Int
    ) {
        try {
            val movimientoApi = MovimientoApi(
                tipo = movimiento.tipo,
                categoria = movimiento.categoria,
                monto = movimiento.monto,
                fecha = movimiento.fecha,
                descripcion = movimiento.descripcion
            )

            val movimientoCreado = remoteRepository.crearMovimiento(movimientoApi)

            movimientoCreado.id?.let { backendId ->
                repository.actualizarBackendId(
                    id = idLocal,
                    backendId = backendId
                )
            }

            _mensajeBackend.value = "Movimiento enviado al backend correctamente"
        } catch (e: Exception) {
            _mensajeBackend.value = "El movimiento se guardó localmente, pero no se pudo enviar al backend"
        }
    }
}