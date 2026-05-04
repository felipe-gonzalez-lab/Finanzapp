package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.data.local.entity.Movimiento
import com.example.finanzapp.data.model.CategoriasFinancieras
import com.example.finanzapp.viewmodel.MovimientoViewModel

@Composable
fun PantallaRegistro(
    viewModel: MovimientoViewModel,
    onVolver: () -> Unit,
    movimientoEditar: Movimiento? = null
) {
    var tipo by remember { mutableStateOf(movimientoEditar?.tipo ?: "") }
    var categoria by remember { mutableStateOf(movimientoEditar?.categoria ?: "") }
    var monto by remember { mutableStateOf(movimientoEditar?.monto?.toString() ?: "") }
    var fecha by remember { mutableStateOf(movimientoEditar?.fecha ?: "") }
    var descripcion by remember { mutableStateOf(movimientoEditar?.descripcion ?: "") }

    var mostrarError by remember { mutableStateOf(false) }
    var mostrarTipos by remember { mutableStateOf(false) }
    var mostrarCategorias by remember { mutableStateOf(false) }

    val montoDouble = monto.toDoubleOrNull()
    val categoriasDisponibles = CategoriasFinancieras.obtenerCategoriasPorTipo(tipo)

    fun fechaValida(fechaTexto: String): Boolean {
        val regex = Regex("""^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01])$""")

        if (!regex.matches(fechaTexto)) {
            return false
        }

        val partes = fechaTexto.split("-")
        val anio = partes[0].toInt()
        val mes = partes[1].toInt()
        val dia = partes[2].toInt()

        val esBisiesto = (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0)

        val diasDelMes = when (mes) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (esBisiesto) 29 else 28
            else -> 0
        }

        return dia <= diasDelMes
    }

    val esFechaValida = fechaValida(fecha)

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { onVolver() }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (movimientoEditar == null) "Registrar Movimiento" else "Editar Movimiento",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box {
            OutlinedButton(
                onClick = { mostrarTipos = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (tipo.isBlank()) "Seleccione tipo" else tipo)
            }

            DropdownMenu(
                expanded = mostrarTipos,
                onDismissRequest = { mostrarTipos = false }
            ) {
                CategoriasFinancieras.tipos.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipo = opcion
                            categoria = ""
                            mostrarTipos = false
                        }
                    )
                }
            }
        }

        if (mostrarError && tipo.isBlank()) {
            Text("Debe seleccionar un tipo", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box {
            OutlinedButton(
                onClick = { mostrarCategorias = true },
                modifier = Modifier.fillMaxWidth(),
                enabled = tipo.isNotBlank()
            ) {
                Text(if (categoria.isBlank()) "Seleccione categoría" else categoria)
            }

            DropdownMenu(
                expanded = mostrarCategorias,
                onDismissRequest = { mostrarCategorias = false }
            ) {
                categoriasDisponibles.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            categoria = opcion
                            mostrarCategorias = false
                        }
                    )
                }
            }
        }

        if (mostrarError && categoria.isBlank()) {
            Text("Debe seleccionar una categoría", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            isError = mostrarError && (monto.isBlank() || montoDouble == null || montoDouble <= 0),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha (YYYY-MM-DD)") },
            isError = mostrarError && (fecha.isBlank() || !esFechaValida),
            modifier = Modifier.fillMaxWidth()
        )

        if (mostrarError && fecha.isNotBlank() && !esFechaValida) {
            Text(
                text = "Ingrese una fecha válida con formato YYYY-MM-DD.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        if (mostrarError) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Complete los campos obligatorios correctamente.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (
                tipo.isBlank() ||
                categoria.isBlank() ||
                monto.isBlank() ||
                montoDouble == null ||
                montoDouble <= 0 ||
                fecha.isBlank() ||
                !esFechaValida
            ) {
                mostrarError = true
            } else {
                val movimiento = Movimiento(
                    id = movimientoEditar?.id ?: 0,
                    tipo = tipo,
                    categoria = categoria,
                    monto = montoDouble,
                    fecha = fecha,
                    descripcion = descripcion
                )

                if (movimientoEditar == null) {
                    viewModel.insertarMovimiento(movimiento)
                } else {
                    viewModel.actualizarMovimiento(movimiento)
                }

                onVolver()
            }
        }) {
            Text(if (movimientoEditar == null) "Guardar" else "Actualizar")
        }
    }
}