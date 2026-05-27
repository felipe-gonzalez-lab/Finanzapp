package com.example.finanzapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

object ValidadorMovimiento {

    fun esMontoValido(monto: String): Boolean {
        val montoDouble = monto.toDoubleOrNull()
        return montoDouble != null && montoDouble > 0
    }

    fun esFechaValida(fecha: String): Boolean {
        return try {
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formato.isLenient = false
            formato.parse(fecha)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun esTipoValido(tipo: String): Boolean {
        return tipo == "Ingreso" || tipo == "Gasto"
    }

    fun esCategoriaValida(categoria: String): Boolean {
        return categoria.isNotBlank()
    }

    fun esDescripcionValida(descripcion: String): Boolean {
        return descripcion.length <= 100
    }

    fun esMovimientoValido(
        tipo: String,
        categoria: String,
        monto: String,
        fecha: String,
        descripcion: String
    ): Boolean {
        return esTipoValido(tipo) &&
                esCategoriaValida(categoria) &&
                esMontoValido(monto) &&
                esFechaValida(fecha) &&
                esDescripcionValida(descripcion)
    }
}