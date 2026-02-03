package ec.prueba.back.segurity.service.implementation;

import ec.prueba.back.segurity.model.Usuario;
import ec.prueba.back.segurity.repository.SecurityUsuarioRepository;
import ec.prueba.back.segurity.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private final SecurityUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private void usernameOrEmailExists(String username, String email){
        if(usuarioRepository.existsByUsername(username)){
            throw new DuplicateKeyException("El username " + username + " ya fue registrado");
        }
        if (usuarioRepository.existsByEmail(email)){
            throw new DuplicateKeyException("El correo " + email + " ya fue regisrado por otro usuario");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        this.usernameOrEmailExists(usuario.getUsername(), usuario.getEmail());

        Usuario nuevoUsuario = Usuario.builder()
                .nombres(usuario.getNombres())
                .apellidos(usuario.getApellidos())
                .email(usuario.getEmail())
                .username(usuario.getUsername())
                .password(passwordEncoder.encode(usuario.getPassword()))
                .build();

        return usuarioRepository.save(nuevoUsuario);
    }
}
