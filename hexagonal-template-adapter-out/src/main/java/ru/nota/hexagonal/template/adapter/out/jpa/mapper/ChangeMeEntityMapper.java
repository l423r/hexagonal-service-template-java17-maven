package ru.nota.hexagonal.template.adapter.out.jpa.mapper;

import org.mapstruct.Mapper;
import ru.nota.hexagonal.template.adapter.out.jpa.entity.ChangeMeEntity;
import ru.nota.hexagonal.template.domain.ChangeMe;

@Mapper(componentModel = "spring")
public interface ChangeMeEntityMapper {

    ChangeMeEntity toEntity(ChangeMe source);

    ChangeMe toDomainModel(ChangeMeEntity source);
}
