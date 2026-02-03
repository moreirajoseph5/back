package ec.prueba.back.segurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.prueba.back.segurity.dto.SigninRequest;
import ec.prueba.back.segurity.dto.TokenDto;
import ec.prueba.back.segurity.jwt.JwtUtils;
import ec.prueba.back.segurity.service.declare.UsuariosService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosService usuariosService;


    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SigninRequest loginRequest, HttpServletResponse response) throws IOException {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken((UserDetails) authentication.getPrincipal());
                LocalDateTime horaActual = LocalDateTime.now();

                // Sumamos 2 horas a la hora actual
                LocalDateTime nuevaHora = horaActual.plusHours(2);

                // Configura la respuesta con el token JWT y otra información
                TokenDto tokenDto = new TokenDto();
                tokenDto.setAccess_token(token);
                tokenDto.setToken_type("Bearer");
                tokenDto.setExp(2L);
                tokenDto.setExpires_in(String.valueOf(nuevaHora));
                tokenDto.setRol(usuariosService.obtenerRolUsuarios(loginRequest.getUsername()));

                // Agrega el token a la cabecera de autorización de la respuesta
                //response.addHeader("User", loginRequest.getUsername());
                response.addHeader("Authorization", "Bearer " + token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpStatus.OK.value());
                response.getWriter().write(new ObjectMapper().writeValueAsString(tokenDto));
                response.getWriter().flush();

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña Invalido");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUserInfo(@RequestParam("token") String token) {
        // Validar el token y extraer la inforvmación del usuario
        String userDetails = jwtUtils.extractUsername(token);
        if (userDetails != null) {
            return ResponseEntity.ok(userDetails);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

}
