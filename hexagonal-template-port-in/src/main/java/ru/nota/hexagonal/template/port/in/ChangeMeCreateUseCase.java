package ru.nota.hexagonal.template.port.in;

import ru.nota.hexagonal.template.port.in.model.ChangeMeCreateCmd;

import java.util.UUID;

public interface ChangeMeCreateUseCase {

    UUID create(ChangeMeCreateCmd cmd);
}
