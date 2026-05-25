package com.finanzapp.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finanzapp.backend.entity.Movimiento;
import com.finanzapp.backend.service.MovimientoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping
    public List<Movimiento> listarMovimientos() {
        return movimientoService.listarMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> buscarMovimientoPorId(@PathVariable Long id) {
        return movimientoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody Movimiento movimiento) {
        Movimiento nuevoMovimiento = movimientoService.guardarMovimiento(movimiento);
        return ResponseEntity.ok(nuevoMovimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimiento> actualizarMovimiento(
            @PathVariable Long id,
            @Valid @RequestBody Movimiento movimientoActualizado
    ) {
        return movimientoService.actualizarMovimiento(id, movimientoActualizado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id) {
        boolean eliminado = movimientoService.eliminarMovimiento(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}