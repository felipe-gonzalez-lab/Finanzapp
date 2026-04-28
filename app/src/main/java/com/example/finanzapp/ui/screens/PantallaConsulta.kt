package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.data.model.CategoriasFinancieras
import com.example.finanzapp.viewmodel.MovimientoViewModel

@Composable
fun PantallaConsulta(
    viewModel: MovimientoViewModel,
    onVolver: () -> Unit
) {
    var tipo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var mes by remember { mutableStateOf("") }

    var mostrarTipos by remember { mutableStateOf(false) }
    var mostrarCategorias by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

    val totalConsulta by viewModel.totalConsulta.collectAsState()
    val categoriasDisponibles = CategoriasFinancieras.obtenerCategoriasPorTipo(tipo)

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { onVolver() }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Consulta por categoría",
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mes,
            onValueChange = { mes = it },
            label = { Text("Mes (YYYY-MM)") },
            modifier = Modifier.fillMaxWidth(),
            isError = mostrarError && mes.isBlank()
        )

        if (mostrarError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Debe seleccionar tipo, categoría y mes.",
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (tipo.isBlank() || categoria.isBlank() || mes.isBlank()) {
                    mostrarError = true
                } else {
                    mostrarError = false
                    viewModel.consultarTotal(categoria, mes)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Consultar total")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (totalConsulta != null) {
            Text(
                text = "Total en $categoria durante $mes: $${totalConsulta ?: 0.0}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}