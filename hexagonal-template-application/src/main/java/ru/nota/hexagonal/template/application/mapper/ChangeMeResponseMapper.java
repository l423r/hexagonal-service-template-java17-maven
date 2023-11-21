package ru.nota.hexagonal.template.application.mapper;

import org.mapstruct.Mapper;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.port.in.model.ChangeMeResponse;

@Mapper(componentModel = "spring")
public interface ChangeMeResponseMapper {

    ChangeMeResponse toResponse(ChangeMe source);

}
