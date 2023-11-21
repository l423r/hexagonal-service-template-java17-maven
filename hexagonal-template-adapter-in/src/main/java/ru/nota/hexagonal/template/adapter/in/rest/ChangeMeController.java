package ru.nota.hexagonal.template.adapter.in.rest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeCreateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeSearchQueryDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeUpdateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.mapper.ChangeMeDTOsMapper;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.port.in.ChangeMeCreateUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeDeleteUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeSearchUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeUpdateUseCase;

import java.util.UUID;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/changeMe")
public class ChangeMeController {

    private final ChangeMeCreateUseCase changeMeCreateUseCase;
    private final ChangeMeUpdateUseCase changeMeUpdateUseCase;
    private final ChangeMeSearchUseCase changeMeSearchUseCase;
    private final ChangeMeDeleteUseCase changeMeDeleteUseCase;
    private final ChangeMeDTOsMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать changeMe")
    public UUID createChangeMe(@Valid @RequestBody ChangeMeCreateDTO dto) {
        final var cmd = mapper.toCreateCmd(dto);
        return changeMeCreateUseCase.create(cmd);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Обновить changeMe")
    public void updateChangeMe(@PathVariable UUID id,
                               @Valid @RequestBody ChangeMeUpdateDTO dto) {
        final var cmd = mapper.toUpdateCmd(dto, id);
        changeMeUpdateUseCase.update(cmd);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск changeMe по id")
    public ChangeMeDTO searchChangeMeById(@PathVariable UUID id) {
        final var changeMeResponse = changeMeSearchUseCase.searchById(SearchByIdRequest.of(id));
        return mapper.toDTO(changeMeResponse);
    }

    @GetMapping
    @Operation(summary = "Постраничный поиск changeMe с сортировкой")
    public Page<ChangeMeDTO> searchPageChangeMe(@Valid ChangeMeSearchQueryDTO queryDTO) {

        final var request = PageSearchRequest.builder()
            .page(queryDTO.getPageNumber())
            .sortField(queryDTO.getSort())
            .pageSize(queryDTO.getPageSize())
            .sortDirection(queryDTO.getSortDirection())
            .build();

        return changeMeSearchUseCase.searchAll(request).map(mapper::toDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить changeMe")
    public void deleteChangeMe(@PathVariable UUID id) {
        changeMeDeleteUseCase.deleteById(DeleteByIdCmd.of(id));
    }
}
