package mx.unam.dgtic.service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import mx.unam.dgtic.dto.EstadoDto;
import mx.unam.dgtic.model.Estado;

public interface IEstadoDtoService {
    public List<EstadoDto> getEstadosList();
    public Optional<EstadoDto> getEstadoById(Integer idEstado);
    public EstadoDto updateEstado(EstadoDto estado) throws ParseException;
    public EstadoDto createEstado(EstadoDto estado) throws ParseException;
    public boolean deleteEstado(Integer idEstado);
    public List<Estado> getEstadosPageable(int page, int size, String dirSort, String sort);
}
