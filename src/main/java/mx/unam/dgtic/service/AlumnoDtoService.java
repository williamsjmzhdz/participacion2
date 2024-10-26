package mx.unam.dgtic.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.unam.dgtic.model.Alumno;
import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.dto.AlumnoDto;
import mx.unam.dgtic.repository.AlumnoRepository;
import mx.unam.dgtic.repository.EstadoRepository;

@Service
public class AlumnoDtoService implements IAlumnoDtoService {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AlumnoDto> getAlumnosList() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return alumnos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public Optional<AlumnoDto> getAlumnoById(String matricula) {
        Optional<Alumno> alumno = alumnoRepository.findById(matricula);
        if (alumno.isPresent()) {
            AlumnoDto alumnoDto = convertToDto(alumno.get());
            return Optional.of(alumnoDto);
        }
        return Optional.empty();
    }

    @Override
    public List<Alumno> getAlumnosPageable(int page, int size, String dirSort, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(dirSort), sort);
        Page<Alumno> pageResult = alumnoRepository.findAll(pageRequest);
        return pageResult.stream().toList();
    }

    @Override
    public AlumnoDto createAlumno(AlumnoDto alumno) throws ParseException {
        Alumno alumnoSalvado = alumnoRepository.save(convertToEntity(alumno));
        return convertToDto(alumnoSalvado);
    }

    @Override
    public AlumnoDto updateAlumno(AlumnoDto alumno) throws ParseException {
        Alumno alumnoActualizado = alumnoRepository.save(convertToEntity(alumno));
        return convertToDto(alumnoActualizado);
    }
    
    @Override
    public boolean deleteAlumno(String matricula) {
        Optional<Alumno> alumno = alumnoRepository.findById(matricula);
        if (alumno.isPresent()) {
            alumnoRepository.delete(alumno.get());
            return true;
        }
        return false;
    }

    @Override
    public List<AlumnoDto> findAlumnosByEstado(String estado) {
        Estado est = estadoRepository.findByEstado(estado);
        List<AlumnoDto> resultado = new LinkedList<>();
        for (Alumno a : est.getAlumnos()) resultado.add(convertToDto(a));
        return resultado;
    }

    private AlumnoDto convertToDto(Alumno alumno) {
        AlumnoDto alumnoDto = modelMapper.map(alumno, AlumnoDto.class);
        if (alumno.getEstado() != null) alumnoDto.setEstado(alumno.getEstado().getEstado());
        if (alumno.getFnac() != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fnacStr = dateFormat.format(alumno.getFnac());
            alumnoDto.setFnac(fnacStr);
        }
        return alumnoDto;
    }

    private Alumno convertToEntity(AlumnoDto alumnoDto) throws ParseException {
        Alumno alumno = modelMapper.map(alumnoDto, Alumno.class);
        if (alumnoDto.getEstado() != null && !alumnoDto.getEstado().isEmpty()) {
            Estado estado = estadoRepository.findByEstado(alumnoDto.getEstado());
            alumno.setEstado(estado);
        }
        if (alumno.getFnac() != null && (!alumnoDto.getFnac().isBlank() && !alumnoDto.getFnac().isEmpty())) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fnacDate = dateFormat.parse(alumnoDto.getFnac());
            alumno.setFnac(fnacDate);
        } else {
            alumno.setFnac(new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01"));
        }
        return alumno;
    }
}
