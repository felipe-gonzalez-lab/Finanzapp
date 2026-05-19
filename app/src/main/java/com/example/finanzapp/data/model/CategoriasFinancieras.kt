package com.example.finanzapp.data.model

object CategoriasFinancieras {

    val tipos = listOf(
        "Ingreso",
        "Gasto"
    )

    private val categoriasIngresosBase = listOf(
        "Sueldo",
        "Bonos y Comisiones",
        "Reembolsos",
        "Inversiones",
        "Ventas",
        "Subsidios y Beneficios",
        "Otros Ingresos"
    )

    private val categoriasGastosBase = listOf(
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

    fun obtenerCategoriasRegistroPorTipo(tipo: String): List<String> {
        return when (tipo) {
            "Ingreso" -> categoriasIngresosBase
            "Gasto" -> categoriasGastosBase
            else -> emptyList()
        }
    }

    fun obtenerCategoriasConsultaPorTipo(tipo: String): List<String> {
        return when (tipo) {
            "Ingreso" -> categoriasIngresosBase + "Total Ingresos"
            "Gasto" -> categoriasGastosBase + "Total Gastos"
            else -> emptyList()
        }
    }
}