package ru.nota.hexagonal.template.port.out;

import org.springframework.data.domain.Page;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import java.util.Optional;

public interface ChangeMePortOut {

    ChangeMe save(ChangeMeSaveCmd cmd);

    Optional<ChangeMe> findById(SearchByIdRequest request);

    boolean isExists(SearchByIdRequest request);

    Page<ChangeMe> findAll(PageSearchRequest request);

    void deleteById(DeleteByIdCmd cmd);
}
