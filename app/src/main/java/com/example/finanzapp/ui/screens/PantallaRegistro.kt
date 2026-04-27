package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.data.local.entity.Movimiento
import com.example.finanzapp.viewmodel.MovimientoViewModel

@Composable
fun PantallaRegistro(
    viewModel: MovimientoViewModel,
    onVolver: () -> Unit
) {
    var tipo by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { onVolver() }) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text("Registrar Movimiento", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (Ingreso/Gasto)") })
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
        OutlinedTextField(value = monto, onValueChange = { monto = it }, label = { Text("Monto") })
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha (YYYY-MM-DD)") })
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val movimiento = Movimiento(
                tipo = tipo,
                categoria = categoria,
                monto = monto.toDoubleOrNull() ?: 0.0,
                fecha = fecha,
                descripcion = descripcion
            )

            viewModel.insertarMovimiento(movimiento)

        }) {
            Text("Guardar")
        }
    }
}