package ru.nota.hexagonal.template.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeCreateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeUpdateDTO;
import ru.nota.hexagonal.template.port.in.model.ChangeMeCreateCmd;
import ru.nota.hexagonal.template.port.in.model.ChangeMeResponse;
import ru.nota.hexagonal.template.port.in.model.ChangeMeUpdateCmd;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ChangeMeDTOsMapper {

    ChangeMeDTO toDTO(ChangeMeResponse source);

    ChangeMeCreateCmd toCreateCmd(ChangeMeCreateDTO source);

    @Mapping(target = "id", expression = "java(id)")
    ChangeMeUpdateCmd toUpdateCmd(ChangeMeUpdateDTO source, UUID id);
}
