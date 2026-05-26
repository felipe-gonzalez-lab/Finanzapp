package com.example.finanzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.finanzapp.data.local.database.AppDatabase
import com.example.finanzapp.repository.MovimientoRepository
import com.example.finanzapp.ui.screens.PantallaPrincipal
import com.example.finanzapp.ui.theme.FinanzappTheme
import com.example.finanzapp.viewmodel.MovimientoViewModel
import com.example.finanzapp.viewmodel.MovimientoViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "finanzapp_db"
        )
            .fallbackToDestructiveMigration(true)
            .build()

        val repository = MovimientoRepository(database.movimientoDao())
        val factory = MovimientoViewModelFactory(repository)

        setContent {
            FinanzappTheme {
                val movimientoViewModel: MovimientoViewModel = viewModel(
                    factory = factory
                )

                PantallaPrincipal(
                    viewModel = movimientoViewModel
                )
            }
        }
    }
}