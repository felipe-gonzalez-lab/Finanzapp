package com.finanzapp.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.finanzapp.backend.entity.Movimiento;
import com.finanzapp.backend.repository.MovimientoRepository;

@Service
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = movimientoRepository;
    }

    public List<Movimiento> listarMovimientos() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> buscarPorId(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento guardarMovimiento(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    public Optional<Movimiento> actualizarMovimiento(Long id, Movimiento movimientoActualizado) {
        return movimientoRepository.findById(id).map(movimientoExistente -> {
            movimientoExistente.setTipo(movimientoActualizado.getTipo());
            movimientoExistente.setCategoria(movimientoActualizado.getCategoria());
            movimientoExistente.setMonto(movimientoActualizado.getMonto());
            movimientoExistente.setFecha(movimientoActualizado.getFecha());
            movimientoExistente.setDescripcion(movimientoActualizado.getDescripcion());

            return movimientoRepository.save(movimientoExistente);
        });
    }

    public boolean eliminarMovimiento(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
            return true;
        }

        return false;
    }
}