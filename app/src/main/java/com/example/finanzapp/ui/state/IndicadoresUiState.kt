package com.example.finanzapp.ui.state

data class IndicadoresUiState(
    val cargando: Boolean = false,
    val dolar: Double? = null,
    val euro: Double? = null,
    val uf: Double? = null,
    val error: String? = null
)