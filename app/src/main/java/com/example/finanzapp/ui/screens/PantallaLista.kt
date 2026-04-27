package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.viewmodel.MovimientoViewModel

@Composable
fun PantallaLista(
    viewModel: MovimientoViewModel,
    onVolver: () -> Unit
) {
    val movimientos by viewModel.movimientos.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarMovimientos()
    }

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { onVolver() }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Lista de movimientos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(movimientos) { movimiento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Tipo: ${movimiento.tipo}")
                        Text("Categoría: ${movimiento.categoria}")
                        Text("Monto: ${movimiento.monto}")
                        Text("Fecha: ${movimiento.fecha}")
                    }
                }
            }
        }
    }
}