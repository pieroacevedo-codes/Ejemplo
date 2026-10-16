package com.example.ejemplo.Security;

import com.example.ejemplo.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Usuario que hace la petición actual, tomado del token por JwtFilter.
 */
@Component
public class UsuarioActual {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // null cuando la petición no trae token (rutas públicas como POST /zonas)
    public Long id_usuario() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        // En rutas públicas Spring pone un usuario "anonymousUser" en lugar de dejarlo vacío
        if (autenticacion == null || autenticacion instanceof AnonymousAuthenticationToken) {
            return null;
        }
        return usuarioRepository.buscar_por_username(autenticacion.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario del token no existe"))
                .getId_usuario();
    }
}
