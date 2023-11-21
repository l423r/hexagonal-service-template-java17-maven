package ru.nota.hexagonal.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nota.hexagonal.template.adapter.out.jpa.mapper.ChangeMeEntityMapper;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;
import ru.nota.hexagonal.template.adapter.out.jpa.service.ChangeMeService;
import ru.nota.hexagonal.template.application.ChangeMeCreateApplicationService;
import ru.nota.hexagonal.template.application.ChangeMeDeleteApplicationService;
import ru.nota.hexagonal.template.application.ChangeMeSearchApplicationService;
import ru.nota.hexagonal.template.application.ChangeMeUpdateApplicationService;
import ru.nota.hexagonal.template.application.mapper.ChangeMeResponseMapper;
import ru.nota.hexagonal.template.port.in.ChangeMeCreateUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeDeleteUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeSearchUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeUpdateUseCase;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;

@Configuration
public class AppConfig {

    @Bean
    public ChangeMePortOut changeMePortOut(ChangeMeRepository changeMeRepository,
                                           ChangeMeEntityMapper changeMeEntityMapper) {
        return new ChangeMeService(changeMeRepository, changeMeEntityMapper);
    }

    @Bean
    public ChangeMeCreateUseCase changeMeCreateUseCase(ChangeMePortOut changeMePortOut) {
        return new ChangeMeCreateApplicationService(changeMePortOut);
    }

    @Bean
    public ChangeMeUpdateUseCase changeMeUpdateUseCase(ChangeMePortOut changeMePortOut) {
        return new ChangeMeUpdateApplicationService(changeMePortOut);
    }

    @Bean
    public ChangeMeSearchUseCase changeMeSearchUseCase(ChangeMePortOut changeMePortOut,
                                                       ChangeMeResponseMapper changeMeResponseMapper) {
        return new ChangeMeSearchApplicationService(changeMePortOut, changeMeResponseMapper);
    }

    @Bean
    public ChangeMeDeleteUseCase changeMeDeleteUseCase(ChangeMePortOut changeMePortOut) {
        return new ChangeMeDeleteApplicationService(changeMePortOut);
    }
}
