package mx.unam.dgtic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.service.AlumnoDtoService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping(path = "/api/v2/alumnos")
public class AlumnoDtoController {
    @Autowired
    AlumnoDtoService alumnoDtoService;

    @GetMapping("/")
    public ResponseEntity<List<AlumnoDto>> getAllDto() {
        return new ResponseEntity<>(alumnoDtoService.getAlumnosList(), HttpStatusCode.valueOf(200));
    }
    
    @GetMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> getDtoById(@PathVariable String matricula) {
        Optional<AlumnoDto> alumnoDto = alumnoDtoService.getAlumnoById(matricula);
        if (alumnoDto.isPresent()) return ResponseEntity.ok(alumnoDto.get());
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping(path = "/")
    public ResponseEntity<AlumnoDto> createAlumnoDto(@RequestBody AlumnoDto alumnoDto) throws ParseException {
        if (alumnoDtoService.getAlumnoById(alumnoDto.getMatricula()) == null) {
            return ResponseEntity.ok(alumnoDtoService.createAlumno(alumnoDto));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> modificarAlumno(@PathVariable String matricula, @RequestBody AlumnoDto alumnoDto) throws ParseException{
        alumnoDto.setMatricula(matricula);
        alumnoDtoService.updateAlumno(alumnoDto);
        return ResponseEntity.of(alumnoDtoService.getAlumnoById(matricula));
    }

    @PatchMapping(path = "/{matricula}")
    public ResponseEntity<AlumnoDto> actualizacionParcialAlumno(@PathVariable String matricula, @RequestBody AlumnoDto alumnoDto) throws ParseException{
        Optional<AlumnoDto> alumnoDb = alumnoDtoService.getAlumnoById(matricula);
        if (alumnoDb.isPresent()) {
            AlumnoDto modificable = alumnoDb.get();
            modificable.setMatricula(matricula);
            if (alumnoDto.getNombre() != null) modificable.setNombre(alumnoDto.getNombre());
            if (alumnoDto.getPaterno() != null) modificable.setPaterno(alumnoDto.getPaterno());
            if (alumnoDto.getFnac() != null) modificable.setFnac(alumnoDto.getFnac());
            if (alumnoDto.getEstatura() > 0.0) modificable.setEstatura(alumnoDto.getEstatura());
            if (alumnoDto.getEstado() != null) modificable.setEstado(alumnoDto.getEstado());
            return ResponseEntity.ok(alumnoDtoService.updateAlumno(modificable));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{matricula}")
    public ResponseEntity<?> eliminarAlumno (@PathVariable String matricula) {
        return (alumnoDtoService.deleteAlumno(matricula))?ResponseEntity.ok().build():ResponseEntity.notFound().build();
    }

    @GetMapping("/{estado}")
    public ResponseEntity<List<AlumnoDto>> getDtoByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(alumnoDtoService.findAlumnosByEstado(estado));
    }

    // /api/v2/alumnos/paginado?page=0&size=2&dir=asc&sort=nombre
    @GetMapping("/paginado")
    public ResponseEntity<List<Alumno>> getPaginadoAlumno(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        @RequestParam(defaultValue = "asc") String dir,
        @RequestParam(defaultValue = "matricula") String sort 
        ) {

        return ResponseEntity.ok(alumnoDtoService.getAlumnosPageable(page, size, sort, sort));
    }
    
}
