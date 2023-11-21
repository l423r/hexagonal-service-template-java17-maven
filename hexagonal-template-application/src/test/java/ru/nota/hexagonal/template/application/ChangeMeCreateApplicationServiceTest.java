package ru.nota.hexagonal.template.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.port.in.model.ChangeMeCreateCmd;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_2;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_ID;

@ExtendWith(MockitoExtension.class)
class ChangeMeCreateApplicationServiceTest {

    @Mock
    private ChangeMePortOut changeMePortOut;

    @InjectMocks
    private ChangeMeCreateApplicationService sut;

    @Test
    @DisplayName("Успешное создание ChangeMe")
    void create_success() {
        //Given

        final var cmd = ChangeMeCreateCmd.builder()
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var changeMe = ChangeMe.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        when(changeMePortOut.save(any(ChangeMeSaveCmd.class))).thenReturn(changeMe);

        //When

        final var result = sut.create(cmd);

        //Then

        assertThat(result)
            .isNotNull();

        verify(changeMePortOut, times(1)).save(any(ChangeMeSaveCmd.class));

    }
}