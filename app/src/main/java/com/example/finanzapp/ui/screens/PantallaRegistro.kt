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
    onVolver: () -> Unit,
    movimientoEditar: Movimiento? = null
) {
    var tipo by remember { mutableStateOf(movimientoEditar?.tipo ?: "") }
    var categoria by remember { mutableStateOf(movimientoEditar?.categoria ?: "") }
    var monto by remember { mutableStateOf(movimientoEditar?.monto?.toString() ?: "") }
    var fecha by remember { mutableStateOf(movimientoEditar?.fecha ?: "") }
    var descripcion by remember { mutableStateOf(movimientoEditar?.descripcion ?: "") }

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

        OutlinedTextField(value = tipo, onValueChange = { tipo = it }, label = { Text("Tipo (Ingreso/Gasto)") })
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
        OutlinedTextField(value = monto, onValueChange = { monto = it }, label = { Text("Monto") })
        OutlinedTextField(value = fecha, onValueChange = { fecha = it }, label = { Text("Fecha (YYYY-MM-DD)") })
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val movimiento = Movimiento(
                id = movimientoEditar?.id ?: 0,
                tipo = tipo,
                categoria = categoria,
                monto = monto.toDoubleOrNull() ?: 0.0,
                fecha = fecha,
                descripcion = descripcion
            )

            if (movimientoEditar == null) {
                viewModel.insertarMovimiento(movimiento)
            } else {
                viewModel.actualizarMovimiento(movimiento)
            }

            onVolver()

        }) {
            Text(if (movimientoEditar == null) "Guardar" else "Actualizar")
        }
    }
}