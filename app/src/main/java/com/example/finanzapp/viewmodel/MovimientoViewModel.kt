package com.example.finanzapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.data.local.entity.Movimiento
import com.example.finanzapp.repository.MovimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovimientoViewModel(
    private val repository: MovimientoRepository
) : ViewModel() {

    private val _movimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val movimientos: StateFlow<List<Movimiento>> = _movimientos

    private val _totalConsulta = MutableStateFlow<Double?>(null)
    val totalConsulta: StateFlow<Double?> = _totalConsulta

    fun cargarMovimientos() {
        viewModelScope.launch {
            _movimientos.value = repository.obtenerTodos()
        }
    }

    fun insertarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.insertar(movimiento)
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
}