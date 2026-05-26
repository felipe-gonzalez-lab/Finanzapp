package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.viewmodel.MovimientoRemoteViewModel

@Composable
fun PantallaBackend(
    viewModel: MovimientoRemoteViewModel,
    onVolver: () -> Unit
) {
    val movimientos by viewModel.movimientosBackend.collectAsState()
    val mensaje by viewModel.mensajeBackend.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Prueba Backend",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(text = mensaje)

        Button(
            onClick = { viewModel.cargarMovimientosBackend() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cargar movimientos del backend")
        }

        Button(
            onClick = { viewModel.crearMovimientoBackend() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear movimiento en backend")
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movimientos) { movimiento ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("ID: ${movimiento.id}")
                        Text("Tipo: ${movimiento.tipo}")
                        Text("Categoría: ${movimiento.categoria}")
                        Text("Monto: $${movimiento.monto}")
                        Text("Fecha: ${movimiento.fecha}")
                        Text("Descripción: ${movimiento.descripcion ?: "Sin descripción"}")
                    }
                }
            }
        }
    }
}