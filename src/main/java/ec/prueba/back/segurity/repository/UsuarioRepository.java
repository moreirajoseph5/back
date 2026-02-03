package ec.prueba.back.segurity.repository;

import ec.prueba.back.segurity.model.Rol;
import ec.prueba.back.segurity.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.username = ?1")
    Optional<Usuario> findByUsername(String username);

    @Query(value = "select u from Usuario u where u.email = ?1")
    Optional<Usuario> findByEmail(String email);


    @Query(nativeQuery = true, value = "update usuarios set password = ?1, fecha_modificacion = now() where id = ?2 RETURNING id")
    Long updatePass(String passNew, Integer isUsr);

    //@Query(value = "select u from Usuario u where u.username=?1")
    @Query("SELECT r FROM Usuario u " +
            "JOIN UsuarioRol ur ON u.id = ur.usuario.id " +
            "JOIN Rol r ON ur.rol.id = r.id " +
            "WHERE u.username = :username")
    List<Rol> getUserRol(String username);


}
