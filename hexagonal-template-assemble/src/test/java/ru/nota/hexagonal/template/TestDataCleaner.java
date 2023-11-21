package ru.nota.hexagonal.template;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;

@Component
@Transactional
@RequiredArgsConstructor
public class TestDataCleaner {
    private final ChangeMeRepository changeMeRepository;

    public void changeMeTruncate() {
        changeMeRepository.deleteAll();
    }

}
