package com.example.finanzapp

import com.example.finanzapp.utils.ValidadorMovimiento
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidadorMovimientoTest {

    @Test
    fun montoValido_retornaTrue_cuandoEsMayorACero() {
        val resultado = ValidadorMovimiento.esMontoValido("15000")

        assertTrue(resultado)
    }

    @Test
    fun montoValido_retornaFalse_cuandoEsCero() {
        val resultado = ValidadorMovimiento.esMontoValido("0")

        assertFalse(resultado)
    }

    @Test
    fun montoValido_retornaFalse_cuandoEsNegativo() {
        val resultado = ValidadorMovimiento.esMontoValido("-5000")

        assertFalse(resultado)
    }

    @Test
    fun montoValido_retornaFalse_cuandoTieneTexto() {
        val resultado = ValidadorMovimiento.esMontoValido("abc")

        assertFalse(resultado)
    }

    @Test
    fun fechaValida_retornaTrue_cuandoTieneFormatoCorrecto() {
        val resultado = ValidadorMovimiento.esFechaValida("27/05/2026")

        assertTrue(resultado)
    }

    @Test
    fun fechaValida_retornaFalse_cuandoTieneFormatoIncorrecto() {
        val resultado = ValidadorMovimiento.esFechaValida("2026/05/27")

        assertFalse(resultado)
    }

    @Test
    fun fechaValida_retornaFalse_cuandoDiaNoExiste() {
        val resultado = ValidadorMovimiento.esFechaValida("30/02/2026")

        assertFalse(resultado)
    }

    @Test
    fun fechaValida_retornaFalse_cuandoMesNoExiste() {
        val resultado = ValidadorMovimiento.esFechaValida("15/13/2026")

        assertFalse(resultado)
    }

    @Test
    fun tipoValido_retornaTrue_cuandoEsIngreso() {
        val resultado = ValidadorMovimiento.esTipoValido("Ingreso")

        assertTrue(resultado)
    }

    @Test
    fun tipoValido_retornaTrue_cuandoEsGasto() {
        val resultado = ValidadorMovimiento.esTipoValido("Gasto")

        assertTrue(resultado)
    }

    @Test
    fun tipoValido_retornaFalse_cuandoNoCorresponde() {
        val resultado = ValidadorMovimiento.esTipoValido("Total ingreso")

        assertFalse(resultado)
    }

    @Test
    fun categoriaValida_retornaFalse_cuandoEstaVacia() {
        val resultado = ValidadorMovimiento.esCategoriaValida("")

        assertFalse(resultado)
    }

    @Test
    fun descripcionValida_retornaFalse_cuandoSuperaLos100Caracteres() {
        val descripcionLarga = "a".repeat(101)

        val resultado = ValidadorMovimiento.esDescripcionValida(descripcionLarga)

        assertFalse(resultado)
    }

    @Test
    fun movimientoValido_retornaTrue_cuandoTodosLosDatosSonCorrectos() {
        val resultado = ValidadorMovimiento.esMovimientoValido(
            tipo = "Ingreso",
            categoria = "Sueldo",
            monto = "500000",
            fecha = "27/05/2026",
            descripcion = "Pago mensual"
        )

        assertTrue(resultado)
    }

    @Test
    fun movimientoValido_retornaFalse_cuandoMontoEsInvalido() {
        val resultado = ValidadorMovimiento.esMovimientoValido(
            tipo = "Gasto",
            categoria = "Alimentación",
            monto = "-1000",
            fecha = "27/05/2026",
            descripcion = "Compra supermercado"
        )

        assertFalse(resultado)
    }
}