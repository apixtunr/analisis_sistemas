package com.SystemAnalisys.Project.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class Security extends OncePerRequestFilter {

   @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Solo proteger endpoints que comienzan con /api/, excepto login y logout
        if (path.startsWith("/api/") && !path.equals("/api/login") && !path.equals("/api/logout")) {
            HttpSession session = request.getSession(false); // no crea nueva sesión
            if (session == null || session.getAttribute("usuario") == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.getWriter().write("No autorizado. Inicia sesión primero.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
