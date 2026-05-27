package com.example.finanzapp.data.remote.dto

data class IndicadoresResponse(
    val uf: IndicadorDto,
    val dolar: IndicadorDto,
    val euro: IndicadorDto
)

data class IndicadorDto(
    val codigo: String,
    val nombre: String,
    val unidad_medida: String,
    val fecha: String,
    val valor: Double
)