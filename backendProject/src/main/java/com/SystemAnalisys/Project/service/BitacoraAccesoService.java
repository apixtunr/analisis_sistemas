package com.SystemAnalisys.Project.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.SystemAnalisys.Project.entity.BitacoraAcceso;
import com.SystemAnalisys.Project.entity.TipoAcceso;
import com.SystemAnalisys.Project.repository.BitacoraAccesoRepository;
import com.SystemAnalisys.Project.repository.TipoAccesoRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class BitacoraAccesoService {

    private final BitacoraAccesoRepository bitacoraAccesoRepository;
    private final TipoAccesoRepository tipoAccesoRepository;

    public BitacoraAccesoService(BitacoraAccesoRepository bitacoraAccesoRepository,
                                  TipoAccesoRepository tipoAccesoRepository) {
        this.bitacoraAccesoRepository = bitacoraAccesoRepository;
        this.tipoAccesoRepository = tipoAccesoRepository;
    }

    public void registrarAcceso(String idUsuario, String tipoAccesoNombre, String accion, 
                               HttpServletRequest request, String sesion) {
    // Buscar el objeto TipoAcceso
    Optional<TipoAcceso> tipoAccesoOpt = tipoAccesoRepository.findByNombre(tipoAccesoNombre);
        
    BitacoraAcceso bitacora = new BitacoraAcceso();
    bitacora.setIdUsuario(idUsuario);
    bitacora.setTipoAcceso(tipoAccesoOpt.orElse(null));
    bitacora.setFechaAcceso(new Date());
    bitacora.setDireccionIp(getClientIpAddress(request));
    bitacora.setHttpUserAgent(request.getHeader("User-Agent"));
    bitacora.setAccion(accion);
    bitacora.setSesion(sesion);
        
    // Obtener información adicional del navegador y SO
    String userAgent = request.getHeader("User-Agent");
    bitacora.setBrowser(extractBrowser(userAgent));
    bitacora.setSistemaOperativo(extractOS(userAgent));
    bitacora.setDispositivo(extractDevice(userAgent));
        
    bitacoraAccesoRepository.save(bitacora);
    }
    
    public Integer countFailedAttempts(String idUsuario, Date sinceDate) {
        // Buscar ID del tipo "FALLIDO"
        Optional<TipoAcceso> tipoFallido = tipoAccesoRepository.findByNombre("FALLIDO");
        if (tipoFallido.isPresent()) {
            return bitacoraAccesoRepository.countByUserAndTypeAndDateAfter(idUsuario, tipoFallido.get(), sinceDate);
        }
        return 0;
    }
    
    public List<BitacoraAcceso> getHistorialUsuario(String idUsuario) {
        return bitacoraAccesoRepository.findByUsuarioOrderByFechaDesc(idUsuario);
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
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