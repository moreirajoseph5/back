package ec.prueba.back.segurity.repository;


import ec.prueba.back.segurity.model.UsuarioRol;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer> {
    @Query(value = "select u from UsuarioRol u where u.usuario.id = ?1")
    List<UsuarioRol> findByRoles(Long id_usuario);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuario_roles (id_rol, usuario_id) VALUES (:rolId,:usuarioId )", nativeQuery = true)
    int saveRolUsers(@Param("rolId") Integer rolId, @Param("usuarioId") Long usuarioId);

}
