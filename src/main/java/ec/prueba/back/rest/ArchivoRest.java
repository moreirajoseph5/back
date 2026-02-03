package ec.prueba.back.rest;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ArchivoRest {
    @GetMapping(value = "/archivo", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Carga de archivo *.csv", description = "Endpoint para cargar los diferentes archivos csv")
    private ResponseEntity<?> GetRoles() {
        try {
            return new ResponseEntity<>("Hello", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
