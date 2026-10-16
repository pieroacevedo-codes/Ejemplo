package com.example.ejemplo.Service.Impl;

import com.example.ejemplo.Dto.LoginRespuestaDTO;
import com.example.ejemplo.Dto.UsuarioDTO;
import com.example.ejemplo.Security.JwtUtil;
import com.example.ejemplo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginRespuestaDTO login(UsuarioDTO usuarioDTO) {
        Authentication autenticacion = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getUsername_usuario(), usuarioDTO.getClave_usuario()));
        String nombre_rol = autenticacion.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElseThrow()
                .replace("ROLE_", "");
        return new LoginRespuestaDTO(jwtUtil.generar_token(autenticacion.getName(), nombre_rol), nombre_rol);
    }
}
