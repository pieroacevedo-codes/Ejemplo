package com.example.ejemplo.Service;

import com.example.ejemplo.Dto.LoginRespuestaDTO;
import com.example.ejemplo.Dto.UsuarioDTO;

public interface UsuarioService {

    LoginRespuestaDTO login(UsuarioDTO usuarioDTO);
}
