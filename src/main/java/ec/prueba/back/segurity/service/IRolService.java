package ec.prueba.back.segurity.service;



import ec.prueba.back.segurity.model.Rol;

import java.util.List;

public interface IRolService {
    Rol createRol(Rol rol);
    List<Rol> findAllRoles();

    Rol updateRolById(Integer id, Rol rol);

    Rol findRolById(Integer id);
    void deleteRolById(Integer id);
}
