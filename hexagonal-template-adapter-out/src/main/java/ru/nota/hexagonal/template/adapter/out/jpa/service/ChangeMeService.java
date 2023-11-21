package ru.nota.hexagonal.template.adapter.out.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.nota.hexagonal.template.adapter.out.jpa.entity.ChangeMeEntity;
import ru.nota.hexagonal.template.adapter.out.jpa.mapper.ChangeMeEntityMapper;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import java.util.Optional;

@RequiredArgsConstructor
public class ChangeMeService implements ChangeMePortOut {

    private final ChangeMeRepository repository;
    private final ChangeMeEntityMapper mapper;

    @Override
    public ChangeMe save(ChangeMeSaveCmd cmd) {
        final var entity = ChangeMeEntity.builder()
            .id(cmd.getId())
            .field1(cmd.getField1())
            .field2(cmd.getField2())
            .build();
        return mapper.toDomainModel(repository.save(entity));
    }

    @Override
    public Optional<ChangeMe> findById(SearchByIdRequest request) {
        return repository.findById(request.getId())
            .map(mapper::toDomainModel);
    }

    @Override
    public boolean isExists(SearchByIdRequest request) {
        return repository.existsById(request.getId());
    }

    @Override
    public Page<ChangeMe> findAll(PageSearchRequest request) {
        final var pageRequest = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by(request.getSortDirection(), request.getSortField()));

        return repository.findAll(pageRequest)
            .map(mapper::toDomainModel);
    }

    @Override
    public void deleteById(DeleteByIdCmd cmd) {
        repository.deleteById(cmd.getId());
    }
}
