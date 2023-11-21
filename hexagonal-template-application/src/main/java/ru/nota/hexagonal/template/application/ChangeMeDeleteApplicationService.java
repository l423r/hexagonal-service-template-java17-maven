package ru.nota.hexagonal.template.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.port.in.ChangeMeDeleteUseCase;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChangeMeDeleteApplicationService implements ChangeMeDeleteUseCase {

    private final ChangeMePortOut changeMePortOut;

    @Override
    public void deleteById(DeleteByIdCmd cmd) {
        log.info("Delete changeMe by {}", cmd);

        changeMePortOut.deleteById(cmd);
    }
}
