package mx.unam.dgtic.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.unam.dgtic.dto.EstadoDto;
import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.service.EstadoDtoService;

@RestController
@RequestMapping(path = "/api/v2/estados")
public class EstadoDtoController {
    @Autowired
    EstadoDtoService estadoDtoService;

    @GetMapping("/")
    public ResponseEntity<List<EstadoDto>> getAllDto() {
        return new ResponseEntity<>(estadoDtoService.getEstadosList(), HttpStatusCode.valueOf(200));
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<EstadoDto> getDtoById(@PathVariable int id) {
        Optional<EstadoDto> EstadoDto = estadoDtoService.getEstadoById(id);
        if (EstadoDto.isPresent()) return ResponseEntity.ok(EstadoDto.get());
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping(path = "/")
    public ResponseEntity<EstadoDto> createEstadoDto(@RequestBody EstadoDto EstadoDto) throws ParseException {
        if (estadoDtoService.getEstadoById(EstadoDto.getIdEstado()) == null) {
            return ResponseEntity.ok(estadoDtoService.createEstado(EstadoDto));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EstadoDto> modificarEstado(@PathVariable int id, @RequestBody EstadoDto EstadoDto) throws ParseException{
        EstadoDto.setIdEstado(id);
        EstadoDto est = estadoDtoService.updateEstado(EstadoDto);
        if (est != null) return ResponseEntity.of(estadoDtoService.getEstadoById(id));
        estadoDtoService.createEstado(EstadoDto);
        return new ResponseEntity<>(estadoDtoService.getEstadoById(id).get(), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EstadoDto> actualizacionParcialEstado(@PathVariable int id, @RequestBody EstadoDto EstadoDto) throws ParseException{
        Optional<EstadoDto> estadoDb = estadoDtoService.getEstadoById(id);
        if (estadoDb.isPresent()) {
            EstadoDto modificable = estadoDb.get();
            modificable.setIdEstado(id);
            if (EstadoDto.getEstado() != null) modificable.setEstado(EstadoDto.getEstado());
            if (EstadoDto.getAbreviatura() != null) modificable.setAbreviatura(EstadoDto.getAbreviatura());
            return ResponseEntity.ok(estadoDtoService.updateEstado(modificable));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> eliminarEstado (@PathVariable int id) {
        return (estadoDtoService.deleteEstado(id))?ResponseEntity.ok().build():ResponseEntity.notFound().build();
    }

    // /api/v2/alumnos/paginado?page=0&size=2&dir=asc&sort=nombre
    @GetMapping("/paginado")
    public ResponseEntity<List<Estado>> getPaginadoEstado(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size,
        @RequestParam(defaultValue = "asc") String dir,
        @RequestParam(defaultValue = "idEstado") String sort 
        ) {
        return ResponseEntity.ok(estadoDtoService.getEstadosPageable(page, size, dir, sort));
    }
}
