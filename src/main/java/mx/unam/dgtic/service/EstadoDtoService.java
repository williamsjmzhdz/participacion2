package mx.unam.dgtic.service;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import mx.unam.dgtic.dto.EstadoDto;
import mx.unam.dgtic.model.Estado;
import mx.unam.dgtic.repository.EstadoRepository;

@Service
public class EstadoDtoService implements IEstadoDtoService {
    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<EstadoDto> getEstadosList() {
        List<EstadoDto> res = new LinkedList<>();
        estadoRepository.findAll().forEach(e -> res.add(convertToDto(e)));
        return res;
    }
    public Optional<EstadoDto> getEstadoById(Integer idEstado) {
        Optional<Estado> estOpt = estadoRepository.findById(idEstado);
        if (estOpt.isPresent()) {
            return Optional.of(convertToDto(estOpt.get()));
        }
        return Optional.empty();
    }
    public EstadoDto updateEstado(EstadoDto estado) throws ParseException {
        Optional<Estado> estOpt = estadoRepository.findById(estado.getIdEstado());
        if (estOpt.isPresent()) {
            return convertToDto(estadoRepository.save(convertToEntity(estado)));
        }
        return null;
    }
    public EstadoDto createEstado(EstadoDto estado) throws ParseException {
        return convertToDto(estadoRepository.save(convertToEntity(estado)));
    }
    public boolean deleteEstado(Integer idEstado) {
        Optional<Estado> estOpt = estadoRepository.findById(idEstado);
        if (estOpt.isPresent()) {
            estadoRepository.deleteById(idEstado);
            return true; 
        }
        return false;
    }

    public List<Estado> getEstadosPageable(int page, int size, String dirSort, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(dirSort), sort);
        Page<Estado> pageResult = estadoRepository.findAll(pageRequest);
        return pageResult.stream().toList();
    }

    private EstadoDto convertToDto(Estado estado) {
        EstadoDto estadoDto = modelMapper.map(estado, EstadoDto.class);
        estadoDto.setIdEstado(estado.getIdEstado());
        if (estado.getEstado() != null) estadoDto.setEstado(estado.getEstado());
        if (estado.getAbreviatura() != null) estadoDto.setAbreviatura(estado.getAbreviatura());
        return estadoDto;
    }

    private Estado convertToEntity(EstadoDto estadoDto) throws ParseException {
        Estado estado = modelMapper.map(estadoDto, Estado.class);
        estado.setIdEstado(estadoDto.getIdEstado());
        if (estadoDto.getEstado() != null) estado.setEstado(estadoDto.getEstado());
        if (estadoDto.getAbreviatura() != null) estado.setAbreviatura(estadoDto.getAbreviatura());
        return estado;
    }
}
