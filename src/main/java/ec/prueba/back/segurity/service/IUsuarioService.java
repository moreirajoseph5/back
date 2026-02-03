package ec.prueba.back.segurity.service;



import ec.prueba.back.segurity.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> findAllUsuarios();
    Usuario createUsuario(Usuario usuario);
}
