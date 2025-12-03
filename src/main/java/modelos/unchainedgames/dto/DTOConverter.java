package modelos.unchainedgames.dto;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DTOConverter {

    // Convierte un DTO a Entidad usando un método de conversión
    public <D, E> E toEntity(D dto, Function<D, E> converter) {
        return dto != null ? converter.apply(dto) : null;
    }

    // Convierte una Entidad a DTO usando un método de conversión
    public <E, D> D toDto(E entity, Function<E, D> converter) {
        return entity != null ? converter.apply(entity) : null;
    }

    // Convierte lista de DTOs a Entidades
    public <D, E> List<E> toEntityList(List<D> dtos, Function<D, E> converter) {
        return dtos != null ?
                dtos.stream()
                        .map(converter)
                        .collect(Collectors.toList()) :
                null;
    }

    // Convierte lista de Entidades a DTOs
    public <E, D> List<D> toDtoList(List<E> entities, Function<E, D> converter) {
        return entities != null ?
                entities.stream()
                        .map(converter)
                        .collect(Collectors.toList()) :
                null;
    }
}