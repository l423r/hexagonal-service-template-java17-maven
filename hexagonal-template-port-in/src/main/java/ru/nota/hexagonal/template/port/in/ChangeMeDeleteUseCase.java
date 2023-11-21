package ru.nota.hexagonal.template.port.in;

import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;

public interface ChangeMeDeleteUseCase {

    void deleteById(DeleteByIdCmd cmd);
}
