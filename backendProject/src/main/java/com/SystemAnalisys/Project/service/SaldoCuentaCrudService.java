package com.SystemAnalisys.Project.service;

import com.SystemAnalisys.Project.dto.SaldoCuentaRequest;
import com.SystemAnalisys.Project.entity.SaldoCuenta;
import com.SystemAnalisys.Project.repository.SaldoCuentaRepository;
import com.SystemAnalisys.Project.repository.PersonaRepository;
import com.SystemAnalisys.Project.entity.Persona;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class SaldoCuentaCrudService {

    private final SaldoCuentaRepository repository;
    private final PersonaRepository personaRepository;

    public SaldoCuentaCrudService(SaldoCuentaRepository repository, PersonaRepository personaRepository) {
        this.repository = repository;
        this.personaRepository = personaRepository;
    }

    public SaldoCuenta create(SaldoCuentaRequest req) {
        SaldoCuenta s = new SaldoCuenta();
        s.setIdpersona(req.getIdpersona());
        s.setIdstatuscuenta(req.getIdstatuscuenta());
        s.setIdtiposaldocuenta(req.getIdtiposaldocuenta());
        s.setSaldoanterior(req.getSaldoanterior());
        s.setDebitos(req.getDebitos());
        s.setCreditos(req.getCreditos());
        s.setFechacreacion(req.getFechacreacion() == null ? new Date() : req.getFechacreacion());
        s.setUsuariocreacion(req.getUsuariocreacion());
        return repository.save(s);
    }

    public Optional<SaldoCuenta> getById(Integer id) {
        return repository.findById(id);
    }

    public List<SaldoCuenta> listAll() {
        return repository.findAll();
    }

    public List<SaldoCuenta> listByPersona(Integer idPersona) {
        return repository.findByIdpersona(idPersona);
    }

    public SaldoCuenta update(Integer id, SaldoCuentaRequest req) {
        Optional<SaldoCuenta> existing = repository.findById(id);
        if (existing.isEmpty()) return null;
        SaldoCuenta s = existing.get();
        s.setIdstatuscuenta(req.getIdstatuscuenta());
        s.setIdtiposaldocuenta(req.getIdtiposaldocuenta());
        s.setSaldoanterior(req.getSaldoanterior());
        s.setDebitos(req.getDebitos());
        s.setCreditos(req.getCreditos());
        s.setFechamodificacion(new Date());
        s.setUsuariomodificacion(req.getUsuariocreacion());
        return repository.save(s);
    }

    /**
     * Recalcula el saldo actual usando la fórmula:
     * SaldoActual = SaldoAnterior + Debitos - Creditos
     * En la implementación actual sobrescribimos `saldoanterior` con el nuevo saldo
     * y guardamos fecha/usuario de modificación.
     *
     * @param id Id de la cuenta
     * @param usuarioUsuarioNombre nombre del usuario que realiza la operación (puede ser null)
     * @return SaldoCuenta actualizado o null si no existe
     */
    public SaldoCuenta recalculateSaldo(Integer id, String usuarioUsuarioNombre) {
        Optional<SaldoCuenta> existing = repository.findById(id);
        if (existing.isEmpty()) return null;
        SaldoCuenta s = existing.get();
        java.math.BigDecimal saldoAnterior = s.getSaldoanterior() == null ? java.math.BigDecimal.ZERO : s.getSaldoanterior();
        java.math.BigDecimal debitos = s.getDebitos() == null ? java.math.BigDecimal.ZERO : s.getDebitos();
        java.math.BigDecimal creditos = s.getCreditos() == null ? java.math.BigDecimal.ZERO : s.getCreditos();
        java.math.BigDecimal saldoActual = saldoAnterior.add(debitos).subtract(creditos);
        s.setSaldoanterior(saldoActual);
        s.setFechamodificacion(new Date());
        s.setUsuariomodificacion(usuarioUsuarioNombre);
        return repository.save(s);
    }

    /**
     * Calcula el saldo actual sin persistir.
     * @param id Id de la cuenta
     * @return saldo actual calculado o null si no existe la cuenta
     */
    public java.math.BigDecimal calculateSaldoActual(Integer id) {
        Optional<SaldoCuenta> existing = repository.findById(id);
        if (existing.isEmpty()) return null;
        SaldoCuenta s = existing.get();
        java.math.BigDecimal saldoAnterior = s.getSaldoanterior() == null ? java.math.BigDecimal.ZERO : s.getSaldoanterior();
        java.math.BigDecimal debitos = s.getDebitos() == null ? java.math.BigDecimal.ZERO : s.getDebitos();
        java.math.BigDecimal creditos = s.getCreditos() == null ? java.math.BigDecimal.ZERO : s.getCreditos();
        return saldoAnterior.add(debitos).subtract(creditos);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    // Método para consultar saldos por nombre y apellido
    public List<SaldoCuenta> consultarPorNombreApellido(String nombre, String apellido) {
        // Buscar personas por nombre y apellido
        List<Persona> personas = personaRepository.findByNombreContainingIgnoreCaseAndApellidoContainingIgnoreCase(nombre, apellido);
        
        List<SaldoCuenta> todasLasCuentas = new ArrayList<>();
        
        // Para cada persona encontrada, buscar sus cuentas
        for (Persona persona : personas) {
            List<SaldoCuenta> cuentasPersona = repository.findByIdpersona(persona.getIdPersona().intValue());
            todasLasCuentas.addAll(cuentasPersona);
        }
        
        return todasLasCuentas;
    }
}
