package ru.nota.hexagonal.template.port.in;

import org.springframework.data.domain.Page;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.port.in.model.ChangeMeResponse;

public interface ChangeMeSearchUseCase {

    Page<ChangeMeResponse> searchAll(PageSearchRequest request);

    ChangeMeResponse searchById(SearchByIdRequest request);
}
