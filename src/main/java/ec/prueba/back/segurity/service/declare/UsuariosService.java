package ec.prueba.back.segurity.service.declare;

import ec.prueba.back.segurity.dto.usuarios.NewPasswordDto;
import ec.prueba.back.segurity.dto.usuarios.RolDto;
import ec.prueba.back.segurity.dto.usuarios.UsuarioDto;
import ec.prueba.back.segurity.dto.usuarios.UsuarioRequestDto;
import ec.prueba.back.utils.PagedResponse;

import java.util.List;

public interface UsuariosService {
    PagedResponse<UsuarioDto> obtenerUsuarios(int page, int size);

    List<RolDto> getAllRoles();

    String crearUsuario(UsuarioRequestDto usuario);

    String actualizarPassword(NewPasswordDto newPasswordDto);

    List<RolDto> obtenerRolUsuarios(String usuario);
}
