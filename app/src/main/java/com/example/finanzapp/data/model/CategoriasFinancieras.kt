package com.example.finanzapp.data.model

object CategoriasFinancieras {

    val tipos = listOf(
        "Ingreso",
        "Gasto"
    )

    val categoriasIngresos = listOf(
        "Sueldo",
        "Bonos y Comisiones",
        "Reembolsos",
        "Inversiones",
        "Ventas",
        "Subsidios y Beneficios",
        "Otros Ingresos"
    )

    val categoriasGastos = listOf(
        "Vivienda",
        "Alimentación",
        "Transporte",
        "Salud",
        "Servicios básicos",
        "Educación",
        "Deudas y Créditos",
        "Seguros",
        "Ahorro e Inversiones",
        "Entretenimiento y Ocio",
        "Otros Gastos"
    )

    fun obtenerCategoriasPorTipo(tipo: String): List<String> {
        return when (tipo) {
            "Ingreso" -> categoriasIngresos
            "Gasto" -> categoriasGastos
            else -> emptyList()
        }
    }
}