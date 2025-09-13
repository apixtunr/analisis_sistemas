package com.SystemAnalisys.Project.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.SystemAnalisys.Project.entity.BitacoraAcceso;
import com.SystemAnalisys.Project.entity.TipoAcceso;
import com.SystemAnalisys.Project.repository.BitacoraAccesoRepository;
import com.SystemAnalisys.Project.repository.TipoAccesoRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BitacoraAccesoService {

    private final BitacoraAccesoRepository bitacoraAccesoRepository;
    private final TipoAccesoRepository tipoAccesoRepository;

    // Cache en memoria para tipos de acceso
    private Map<String, TipoAcceso> tiposAccesoCache;

    public BitacoraAccesoService(BitacoraAccesoRepository bitacoraAccesoRepository,
                                 TipoAccesoRepository tipoAccesoRepository) {
        this.bitacoraAccesoRepository = bitacoraAccesoRepository;
        this.tipoAccesoRepository = tipoAccesoRepository;
    }

    // Cargar cache de tipos de acceso al iniciar la aplicación
    @PostConstruct
    private void cargarTiposAcceso() {
        tiposAccesoCache = tipoAccesoRepository.findAll()
                                .stream()
                                .collect(Collectors.toMap(TipoAcceso::getNombre, t -> t));
    }

    public void registrarAcceso(String idUsuario, String tipoAccesoNombre, String accion, 
                                HttpServletRequest request, String sesion) {

        TipoAcceso tipoAcceso = tiposAccesoCache.get(tipoAccesoNombre);
        if (tipoAcceso == null) {
            throw new RuntimeException("Tipo de acceso no encontrado en BD: " + tipoAccesoNombre);
        }

        BitacoraAcceso bitacora = new BitacoraAcceso();
        bitacora.setIdUsuario(idUsuario);
        bitacora.setTipoAcceso(tipoAcceso);
        bitacora.setFechaAcceso(new Date());
        bitacora.setDireccionIp(getClientIpAddress(request));
        bitacora.setHttpUserAgent(request.getHeader("User-Agent"));
        bitacora.setAccion(accion);
        bitacora.setSesion(sesion);

        // Info adicional del navegador y SO
        String userAgent = request.getHeader("User-Agent");
        bitacora.setBrowser(extractBrowser(userAgent));
        bitacora.setSistemaOperativo(extractOS(userAgent));
        bitacora.setDispositivo(extractDevice(userAgent));

        bitacoraAccesoRepository.save(bitacora);
    }

    public Integer countFailedAttempts(String idUsuario, Date sinceDate) {
        TipoAcceso tipoFallido = tiposAccesoCache.get("Bloqueado - Password incorrecto/Numero de intentos exedidos");
        if (tipoFallido != null) {
            return bitacoraAccesoRepository.countByUserAndTypeAndDateAfter(idUsuario, tipoFallido, sinceDate);
        }
        return 0;
    }

    public List<BitacoraAcceso> getHistorialUsuario(String idUsuario) {
        return bitacoraAccesoRepository.findByUsuarioOrderByFechaDesc(idUsuario);
    }

    // -------------------- Métodos auxiliares --------------------

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        return (xForwardedForHeader == null) ? request.getRemoteAddr() : xForwardedForHeader.split(",")[0];
    }

    private String extractBrowser(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Chrome")) return "Chrome";
        if (userAgent.contains("Firefox")) return "Firefox";
        if (userAgent.contains("Safari")) return "Safari";
        if (userAgent.contains("Edge")) return "Edge";
        return "Other";
    }

    private String extractOS(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Windows")) return "Windows";
        if (userAgent.contains("Mac")) return "MacOS";
        if (userAgent.contains("Linux")) return "Linux";
        if (userAgent.contains("Android")) return "Android";
        if (userAgent.contains("iOS")) return "iOS";
        return "Other";
    }

    private String extractDevice(String userAgent) {
        if (userAgent == null) return "Unknown";
        if (userAgent.contains("Mobile")) return "Mobile";
        if (userAgent.contains("Tablet")) return "Tablet";
        return "Desktop";
    }
}
