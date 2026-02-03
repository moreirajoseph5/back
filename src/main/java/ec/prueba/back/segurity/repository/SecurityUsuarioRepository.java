package ec.prueba.back.segurity.repository;



import ec.prueba.back.segurity.model.Usuario;
import ec.prueba.back.segurity.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecurityUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    @Query(value = "select u from UsuarioRol u where u.usuario.username = ?1")
    List<UsuarioRol> findByUsername2(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
