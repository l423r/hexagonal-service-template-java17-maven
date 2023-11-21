package ru.nota.hexagonal.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.port.in.ChangeMeCreateUseCase;
import ru.nota.hexagonal.template.port.in.model.ChangeMeCreateCmd;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import java.util.UUID;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChangeMeCreateApplicationService implements ChangeMeCreateUseCase {

    private final ChangeMePortOut changeMePortOut;

    @Override
    public UUID create(ChangeMeCreateCmd cmd) {
        log.info("Create changeMe by {}", cmd);

        final var changeMeSaveCmd = ChangeMeSaveCmd.builder()
            .field1(cmd.getField1())
            .field2(cmd.getField2())
            .id(UUID.randomUUID())
            .build();

        return changeMePortOut.save(changeMeSaveCmd).getId();
    }
}
