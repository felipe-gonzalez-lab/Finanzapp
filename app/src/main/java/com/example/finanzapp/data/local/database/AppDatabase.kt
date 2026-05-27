package com.example.finanzapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finanzapp.data.local.dao.MovimientoDao
import com.example.finanzapp.data.local.entity.Movimiento

@Database(
    entities = [Movimiento::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movimientoDao(): MovimientoDao
}