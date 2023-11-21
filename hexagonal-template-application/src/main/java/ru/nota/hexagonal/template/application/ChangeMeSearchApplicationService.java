package ru.nota.hexagonal.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.application.mapper.ChangeMeResponseMapper;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.exception.NotFoundException;
import ru.nota.hexagonal.template.port.in.ChangeMeSearchUseCase;
import ru.nota.hexagonal.template.port.in.model.ChangeMeResponse;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChangeMeSearchApplicationService implements ChangeMeSearchUseCase {

    private final ChangeMePortOut changeMePortOut;
    private final ChangeMeResponseMapper mapper;

    @Override
    public Page<ChangeMeResponse> searchAll(PageSearchRequest request) {
        log.info("Search all changeMe by {}", request);

        return changeMePortOut.findAll(request)
            .map(mapper::toResponse);
    }

    @Override
    public ChangeMeResponse searchById(SearchByIdRequest request) {
        log.info("Search changeMe by {}", request);

        return changeMePortOut.findById(request)
            .map(mapper::toResponse)
            .orElseThrow(NotFoundException::new);
    }
}
