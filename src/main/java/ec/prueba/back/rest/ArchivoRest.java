package ec.prueba.back.rest;

import ec.prueba.back.service.declare.CommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ArchivoRest {

    @Autowired
    private CommerceService service;



    @PostMapping(
            value = "/archivo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file
    ) {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Archivo vacío");
        }

        try {
            service.processCsv(file);
            return ResponseEntity.ok(
                    Map.of("mensaje", "Archivo cargado correctamente")
            );
        } catch (Exception e) {
            e.printStackTrace(); // 👈 clave para debug
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            Map.of(
                                    "error", "Error procesando CSV",
                                    "detalle", e.getMessage()
                            )
                    );
        }
    }


    // POST VALIDAR
    @PostMapping("/validar")
    public ResponseEntity<?> validate(@RequestParam LocalDate processDate) {
        int total = service.validate(processDate);
        return ResponseEntity.ok(Map.of("registrosEnCuarentena", total));
    }

    // GET QUARANTINE
    @GetMapping("/quarantine")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                service.listQuarantine(PageRequest.of(page, size))
        );
    }
}
