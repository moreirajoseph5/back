package ec.prueba.back.segurity.service.implementation;


import ec.prueba.back.segurity.dto.usuarios.NewPasswordDto;
import ec.prueba.back.segurity.dto.usuarios.RolDto;
import ec.prueba.back.segurity.dto.usuarios.UsuarioDto;
import ec.prueba.back.segurity.dto.usuarios.UsuarioRequestDto;
import ec.prueba.back.segurity.model.Rol;
import ec.prueba.back.segurity.model.Usuario;
import ec.prueba.back.segurity.model.UsuarioRol;
import ec.prueba.back.segurity.repository.RolRepository;
import ec.prueba.back.segurity.repository.UsuarioRepository;
import ec.prueba.back.segurity.repository.UsuarioRolRepository;
import ec.prueba.back.segurity.service.declare.UsuariosService;
import ec.prueba.back.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosServiceImpl implements UsuariosService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;


    @Override
    public PagedResponse<UsuarioDto> obtenerUsuarios(int page, int size) {
        List<UsuarioDto> lstUsuariosDto = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> pageUsuarios = usuarioRepository.findAll(pageable);
        List<Usuario> lstUsuarios = pageUsuarios.getContent();

        for (Usuario usuario : lstUsuarios) {
            List<UsuarioRol> usuarioRolList = usuarioRolRepository.findByRoles(usuario.getId());
            List<RolDto> roles = new ArrayList<>();
            for (UsuarioRol usuarioRol : usuarioRolList) {
                Rol rol = usuarioRol.getRol();
                RolDto rolDto = new RolDto();
                rolDto.setId(rol.getId());
                rolDto.setNombre(rol.getNombre());
                roles.add(rolDto);
            }
            UsuarioDto usuarioDto = new UsuarioDto();
            usuarioDto.setId(usuario.getId());
            usuarioDto.setTipoIdentificacion(usuario.getTipoIdentificacion());
            usuarioDto.setIdentificacion(usuario.getIdentificacion());
            usuarioDto.setNombres(usuario.getNombres());
            usuarioDto.setApellidos(usuario.getApellidos());
            usuarioDto.setEmail(usuario.getEmail());
            usuarioDto.setUsername(usuario.getUsername());
            usuarioDto.setTelefono(usuario.getTelefono());
            usuarioDto.setDireccion(usuario.getDireccion());
            usuarioDto.setSexo(usuario.getSexo());
            usuarioDto.setActivo(usuario.getActivo());
            usuarioDto.setEsPrimerInicio(usuario.getEsPrimerInicio());
            usuarioDto.setFechaCreacion(usuario.getFechaCreacion());
            usuarioDto.setFechaModificacion(usuario.getFechaModificacion());
            usuarioDto.setRoles(roles);
            lstUsuariosDto.add(usuarioDto);
        }

        PagedResponse<UsuarioDto> response = new PagedResponse<>();
        response.setContent(lstUsuariosDto);
        response.setPage(pageUsuarios.getNumber());
        response.setSize(pageUsuarios.getSize());
        response.setTotalElements(pageUsuarios.getTotalElements());
        response.setTotalPages(pageUsuarios.getTotalPages());

        return response;
    }

    @Override
    public List<RolDto> getAllRoles() {
        List<RolDto> rolDtoList = new ArrayList<>();
        List<Rol> roles = rolRepository.findAll();
        for (Rol rol : roles) {
            RolDto rolDto = new RolDto();
            rolDto.setId(rol.getId());
            rolDto.setNombre(rol.getNombre());
            rolDtoList.add(rolDto);
        }
        return rolDtoList;
    }

    @Transactional
    @Override
    public String crearUsuario(UsuarioRequestDto usuario) {
        try {
            String resultadoValidacion = validarUsuario(usuario.getEmail(), usuario.getUsername());
            if (resultadoValidacion != null && !resultadoValidacion.isEmpty()) {
                return resultadoValidacion;
            }
            else {
                Usuario usuarioNuevo = new Usuario();

                /* Optener el rol para guardar */
                Rol rol = rolRepository.getReferenceById(usuario.getRolId());

                /* ****************** */

                usuarioNuevo.setActivo(Boolean.TRUE);
                usuarioNuevo.setEmail(usuario.getEmail());
                usuarioNuevo.setUsername(usuario.getUsername());
                usuarioNuevo.setTipoIdentificacion(usuario.getTipoIdentificacion());
                usuarioNuevo.setIdentificacion(usuario.getIdentificacion());
                usuarioNuevo.setDireccion(usuario.getDireccion());
                usuarioNuevo.setNombres(usuario.getNombres());
                usuarioNuevo.setApellidos(usuario.getApellidos());
                usuarioNuevo.setTelefono(usuario.getTelefono());
                usuarioNuevo.setSexo(usuario.getSexo());
                usuarioNuevo.setFechaCreacion(new Date());
                usuarioNuevo.setPassword(crearPassword(usuario.getPassword()));
                Usuario usuarioGuardado = usuarioRepository.save(usuarioNuevo);

                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setRol(rol);
                usuarioRol.setUsuario(usuarioGuardado);
               /* UsuarioRol usuarioRepository1 = usuarioRolRepository.save(usuarioRol);
                System.out.println("***************************");
                System.out.println(usuarioRepository1);
                System.out.println("***************************");/*

                */

                usuarioRolRepository.saveRolUsers(rol.getId(), usuarioGuardado.getId());


                if (usuarioGuardado.getId() != null){
                    return "Usuario creado con exito";
                }
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String actualizarPassword(NewPasswordDto newPasswordDto) {
        Long idUser = usuarioRepository.updatePass(crearPassword(newPasswordDto.getNewPassword()), newPasswordDto.getIdUsuario());
        if (idUser != null) {
            return "Usuario actualizado con exito";
        }
        return "No se pudo actualizar la contraseña, contactese con Sistemas";
    }

    @Override
    public List<RolDto> obtenerRolUsuarios(String usuario) {
        List<RolDto> rolDtoList = new ArrayList<>();
        List<Rol> roles = usuarioRepository.getUserRol(usuario);
        for (Rol rol : roles) {
            RolDto rolDto = new RolDto();
            rolDto.setId(rol.getId());
            rolDto.setNombre(rol.getNombre());
            rolDtoList.add(rolDto);
        }
        return rolDtoList;
    }

    public String crearPassword(String pass) {
        int costFactor = 11;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(costFactor);
        String hashedPassword = passwordEncoder.encode(pass);
        return hashedPassword;
    }

    public String validarUsuario(String email, String username) {
        Boolean result = false;
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isPresent()) {
            return "El usuario ingresado ya existe";
        }
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(email);
        if (usuarioEmail.isPresent()) {
            return "El correo ingresado ya existe";
        }
        return null;
    }

}
