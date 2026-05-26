package com.example.finanzapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.finanzapp.data.local.dao.MovimientoDao
import com.example.finanzapp.data.local.entity.Movimiento

@Database(entities = [Movimiento::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movimientoDao(): MovimientoDao
}