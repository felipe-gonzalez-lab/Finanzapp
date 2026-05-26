package com.example.finanzapp.data.remote

data class MovimientoApi(
    val id: Long? = null,
    val tipo: String,
    val categoria: String,
    val monto: Double,
    val fecha: String,
    val descripcion: String? = null
)