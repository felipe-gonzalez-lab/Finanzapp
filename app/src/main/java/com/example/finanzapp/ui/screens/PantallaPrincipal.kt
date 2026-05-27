package com.example.finanzapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finanzapp.data.local.entity.Movimiento
import com.example.finanzapp.viewmodel.IndicadoresViewModel
import com.example.finanzapp.viewmodel.MovimientoViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PantallaPrincipal(
    viewModel: MovimientoViewModel,
    indicadoresViewModel: IndicadoresViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var mostrarRegistro by remember { mutableStateOf(false) }
    var mostrarLista by remember { mutableStateOf(false) }
    var mostrarConsulta by remember { mutableStateOf(false) }
    var movimientoEditar by remember { mutableStateOf<Movimiento?>(null) }

    val indicadoresState by indicadoresViewModel.indicadoresState.collectAsState()

    val formatoChileno = NumberFormat.getCurrencyInstance(Locale("es", "CL")).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }

    LaunchedEffect(Unit) {
        indicadoresViewModel.cargarIndicadores()
    }

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
            onVolver = {
                mostrarLista = false
            },
            onEditar = { movimiento ->
                movimientoEditar = movimiento
                mostrarLista = false
                mostrarRegistro = true
            }
        )
    } else if (mostrarConsulta) {
        PantallaConsulta(
            viewModel = viewModel,
            onVolver = {
                mostrarConsulta = false
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

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Indicadores económicos",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        when {
                            indicadoresState.cargando -> {
                                Text(text = "Cargando indicadores...")
                            }

                            indicadoresState.error != null -> {
                                Text(text = indicadoresState.error ?: "Error desconocido")
                            }

                            else -> {
                                Text(
                                    text = "Dólar: ${
                                        formatoChileno.format(indicadoresState.dolar ?: 0.0)
                                    }"
                                )
                                Text(
                                    text = "Euro: ${
                                        formatoChileno.format(indicadoresState.euro ?: 0.0)
                                    }"
                                )
                                Text(
                                    text = "UF: ${
                                        formatoChileno.format(indicadoresState.uf ?: 0.0)
                                    }"
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        mostrarRegistro = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrar movimiento")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        mostrarLista = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver movimientos")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        mostrarConsulta = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Consultar por categoría")
                }
            }
        }
    }
}