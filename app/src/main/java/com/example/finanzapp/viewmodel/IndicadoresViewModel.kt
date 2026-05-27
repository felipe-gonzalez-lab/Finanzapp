package com.example.finanzapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finanzapp.data.remote.RetrofitClient
import com.example.finanzapp.ui.state.IndicadoresUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IndicadoresViewModel : ViewModel() {

    private val _indicadoresState = MutableStateFlow(IndicadoresUiState())
    val indicadoresState: StateFlow<IndicadoresUiState> = _indicadoresState

    fun cargarIndicadores() {
        viewModelScope.launch {
            _indicadoresState.value = IndicadoresUiState(cargando = true)

            try {
                val respuesta = RetrofitClient.indicadoresApi.obtenerIndicadores()

                _indicadoresState.value = IndicadoresUiState(
                    cargando = false,
                    dolar = respuesta.dolar.valor,
                    euro = respuesta.euro.valor,
                    uf = respuesta.uf.valor
                )

            } catch (e: Exception) {
                _indicadoresState.value = IndicadoresUiState(
                    cargando = false,
                    error = "No se pudieron cargar los indicadores"
                )
            }
        }
    }
}