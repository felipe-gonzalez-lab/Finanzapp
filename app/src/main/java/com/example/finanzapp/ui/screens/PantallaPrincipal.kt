package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.viewmodel.MovimientoViewModel
import com.example.finanzapp.data.local.entity.Movimiento

@Composable
fun PantallaPrincipal(
    viewModel: MovimientoViewModel
) {
    var mostrarRegistro by remember { mutableStateOf(false) }
    var mostrarLista by remember { mutableStateOf(false) }
    var movimientoEditar by remember { mutableStateOf<Movimiento?>(null) }

    if (mostrarRegistro) {
        PantallaRegistro(
            viewModel = viewModel,
            onVolver = {
                mostrarRegistro = false
                movimientoEditar = null
            },
            movimientoEditar = movimientoEditar
        )
    } else if (mostrarLista) {
        PantallaLista(
            viewModel = viewModel,
            onVolver = { mostrarLista = false },
            onEditar = { movimiento ->
                movimientoEditar = movimiento
                mostrarLista = false
                mostrarRegistro = true
            }
        )
    } else {
        Scaffold { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "Finanzapp",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Aplicación para registrar ingresos y gastos personales."
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { mostrarRegistro = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar movimiento")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { mostrarLista = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver movimientos")
                }
            }
        }
    }
}