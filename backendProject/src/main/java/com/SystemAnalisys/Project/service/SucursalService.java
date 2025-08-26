package com.SystemAnalisys.Project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.entity.Sucursal;
import com.SystemAnalisys.Project.repository.SucursalRepository;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) 
    {
        this.sucursalRepository = sucursalRepository;
    }
    public List<Sucursal> getAllSucursales()
    {
        return (List<Sucursal>) sucursalRepository.findAll();
    }
    public Optional<Sucursal> findByIdSucursal(Integer idSucursal) {
        return sucursalRepository.findByIdSucursal(idSucursal);
    }
    public Sucursal save(Sucursal par_sucursal) {
        return sucursalRepository.save(par_sucursal);
    }
    public void delete(Sucursal par_sucursal) {
         sucursalRepository.delete(par_sucursal);
    }
}
