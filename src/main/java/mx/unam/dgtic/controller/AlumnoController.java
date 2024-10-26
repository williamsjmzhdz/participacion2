package mx.unam.dgtic.controller;

import java.util.List;
import java.util.Optional;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.service.AlumnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/api/alumnos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;
    
    @GetMapping(path = "/")
    public ResponseEntity<List<Alumno>> getAll() {
        return ResponseEntity.ok(alumnoService.getAlumnosList());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Alumno> getById(@PathVariable String id) {
        Optional<Alumno> alumno = alumnoService.getAlumnoById(id);
        if (alumno.isPresent()) return ResponseEntity.ok(alumno.get());
        else return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteAlumno(@PathVariable String id) {
        if (alumnoService.deleteAlumno(id)) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/")
    public ResponseEntity<Alumno> postMethodName(@RequestBody Alumno alumno) {
        Alumno alumnoNuevo = alumnoService.createAlumno(alumno);
        return new ResponseEntity<>(alumnoNuevo, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Alumno> putMethodName(@PathVariable String id, @RequestBody Alumno alumno) {
        Optional<Alumno> alumnoDb = alumnoService.getAlumnoById(id);
        if (alumnoDb.isPresent()) {
            Alumno alumnoToUpdate = alumnoDb.get();
            return ResponseEntity.ok(alumnoService.updateAlumno(alumnoToUpdate));
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Alumno> actualizacionParcial(@PathVariable String id, @RequestBody Alumno alumno) {
        Optional<Alumno> alumnoToUpdate = alumnoService.getAlumnoById(id);
        if (alumnoToUpdate.isPresent()) {
            Alumno alumnoParcial = alumnoToUpdate.get();
            if (alumno.getNombre() != null) alumnoParcial.setNombre(alumno.getNombre());
            if (alumno.getPaterno() != null) alumnoParcial.setPaterno(alumno.getPaterno());
            if (alumno.getFnac() != null) alumnoParcial.setFnac(alumno.getFnac());
            if (alumno.getEstatura() != 0.0) alumnoParcial.setEstatura(alumno.getEstatura());
            return ResponseEntity.ok(alumnoService.updateAlumno(alumnoParcial));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/estados/{edo}")
    public ResponseEntity<List<Alumno>> getByEstado(@PathVariable String edo) {
        return ResponseEntity.ok(alumnoService.findAlumnosByEstado(edo));
    }
    
}