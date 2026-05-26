package com.example.finanzapp.repository

import com.example.finanzapp.data.local.dao.MovimientoDao
import com.example.finanzapp.data.local.entity.Movimiento

class MovimientoRepository(
    private val movimientoDao: MovimientoDao
) {
    suspend fun insertar(movimiento: Movimiento): Long {
        return movimientoDao.insertar(movimiento)
    }

    suspend fun actualizar(movimiento: Movimiento) {
        movimientoDao.actualizar(movimiento)
    }

    suspend fun eliminar(movimiento: Movimiento) {
        movimientoDao.eliminar(movimiento)
    }

    suspend fun obtenerTodos(): List<Movimiento> {
        return movimientoDao.obtenerTodos()
    }

    suspend fun actualizarBackendId(id: Int, backendId: Long) {
        movimientoDao.actualizarBackendId(id, backendId)
    }

    suspend fun totalPorCategoriaMes(categoria: String, mes: String): Double? {
        return movimientoDao.totalPorCategoriaMes(categoria, mes)
    }

    suspend fun totalPorTipoMes(tipo: String, mes: String): Double? {
        return movimientoDao.totalPorTipoMes(tipo, mes)
    }
}