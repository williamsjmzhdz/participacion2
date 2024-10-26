package mx.unam.dgtic.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.model.Alumno;

public interface IAlumnoDtoService {
    public List<AlumnoDto> getAlumnosList();
    public Optional<AlumnoDto> getAlumnoById(String matricula);
    public AlumnoDto updateAlumno(AlumnoDto alumno) throws ParseException;
    public AlumnoDto createAlumno(AlumnoDto alumno) throws ParseException;
    public boolean deleteAlumno(String matricula);
    public List<AlumnoDto> findAlumnosByEstado(String estado);
    public List<Alumno> getAlumnosPageable(int page, int size, String dirSort, String sort);
}
