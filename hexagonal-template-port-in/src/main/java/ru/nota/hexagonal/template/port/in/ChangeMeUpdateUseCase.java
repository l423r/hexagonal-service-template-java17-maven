package ru.nota.hexagonal.template.port.in;

import ru.nota.hexagonal.template.port.in.model.ChangeMeUpdateCmd;

public interface ChangeMeUpdateUseCase {

    void update(ChangeMeUpdateCmd cmd);
}
