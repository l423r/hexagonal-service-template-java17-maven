package ru.nota.hexagonal.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.exception.NotFoundException;
import ru.nota.hexagonal.template.port.in.ChangeMeUpdateUseCase;
import ru.nota.hexagonal.template.port.in.model.ChangeMeUpdateCmd;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChangeMeUpdateApplicationService implements ChangeMeUpdateUseCase {

    private final ChangeMePortOut changeMePortOut;

    @Override
    public void update(ChangeMeUpdateCmd cmd) {
        log.info("Update changeMe by {}", cmd);

        if (!changeMePortOut.isExists(SearchByIdRequest.of(cmd.getId()))) {
            throw new NotFoundException();
        }

        final var changeMeSaveCmd = ChangeMeSaveCmd.builder()
            .field1(cmd.getField1())
            .field2(cmd.getField2())
            .id(cmd.getId())
            .build();

        changeMePortOut.save(changeMeSaveCmd);
    }
}
