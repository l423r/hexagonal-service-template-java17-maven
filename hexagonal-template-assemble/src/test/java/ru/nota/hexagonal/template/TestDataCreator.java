package ru.nota.hexagonal.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.adapter.out.jpa.entity.ChangeMeEntity;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;

import static ru.nota.hexagonal.template.TestDataFactory.getChangeMeEntityBuilder;

@Component
@Transactional
@RequiredArgsConstructor
public class TestDataCreator {
    private final ChangeMeRepository changeMeRepository;

    public ChangeMeEntity changeMe() {
        return changeMe(getChangeMeEntityBuilder().build());
    }

    public ChangeMeEntity changeMe(ChangeMeEntity entity) {
        return changeMeRepository.save(entity);
    }
}
