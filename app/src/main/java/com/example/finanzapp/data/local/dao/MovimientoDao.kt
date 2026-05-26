package com.example.finanzapp.data.local.dao

import androidx.room.*
import com.example.finanzapp.data.local.entity.Movimiento

@Dao
interface MovimientoDao {

    @Insert
    suspend fun insertar(movimiento: Movimiento): Long

    @Update
    suspend fun actualizar(movimiento: Movimiento)

    @Delete
    suspend fun eliminar(movimiento: Movimiento)

    @Query("SELECT * FROM movimientos ORDER BY fecha DESC")
    suspend fun obtenerTodos(): List<Movimiento>

    @Query("UPDATE movimientos SET backendId = :backendId WHERE id = :id")
    suspend fun actualizarBackendId(id: Int, backendId: Long)

    @Query("""
        SELECT SUM(monto) FROM movimientos 
        WHERE categoria = :categoria 
        AND fecha LIKE :mes
    """)
    suspend fun totalPorCategoriaMes(categoria: String, mes: String): Double?

    @Query("""
        SELECT SUM(monto) FROM movimientos 
        WHERE tipo = :tipo 
        AND fecha LIKE :mes
    """)
    suspend fun totalPorTipoMes(tipo: String, mes: String): Double?
}