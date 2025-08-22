package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.Empresa;
import com.SystemAnalisys.Project.repository.EmpresaRepository;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) 
    {
        this.empresaRepository = empresaRepository;
    }
    public List<Empresa> getAllEmpresas()
    {
        return (List<Empresa>) empresaRepository.findAll();
    }
    public Optional<Empresa> findByNit(String par_nit) {
        return empresaRepository.findByNit(par_nit);
    }
    public Optional<Empresa> findById(Integer par_id) {
        return empresaRepository.findById(par_id);
    }
    public Empresa save(Empresa par_empresa) {
        return empresaRepository.save(par_empresa);
    }
    public void delete(Empresa par_empresa) {
         empresaRepository.delete(par_empresa);
    }
}
