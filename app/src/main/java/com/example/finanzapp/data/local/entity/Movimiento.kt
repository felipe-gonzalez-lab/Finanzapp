package com.example.finanzapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class Movimiento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val tipo: String, // Ingreso o Gasto
    val categoria: String,
    val monto: Double,
    val fecha: String,
    val descripcion: String
)