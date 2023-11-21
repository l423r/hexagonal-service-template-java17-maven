package ru.nota.hexagonal.template.application;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.exception.NotFoundException;
import ru.nota.hexagonal.template.port.in.model.ChangeMeUpdateCmd;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_2;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_ID;

@ExtendWith(MockitoExtension.class)
class ChangeMeUpdateApplicationServiceTest {

    @Mock
    private ChangeMePortOut changeMePortOut;

    @InjectMocks
    private ChangeMeUpdateApplicationService sut;

    @Test
    @DisplayName("Успешное обновление ChangeMe")
    void update_success() {
        //Given

        final var cmd = ChangeMeUpdateCmd.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var changeMe = ChangeMe.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        when(changeMePortOut.isExists(SearchByIdRequest.of(CHANGE_ME_ID))).thenReturn(Boolean.TRUE);
        when(changeMePortOut.save(any(ChangeMeSaveCmd.class))).thenReturn(changeMe);

        //When

        final ThrowableAssert.ThrowingCallable result = () -> sut.update(cmd);

        //Then

        assertThatCode(result)
            .doesNotThrowAnyException();

        verify(changeMePortOut, times(1)).isExists(SearchByIdRequest.of(CHANGE_ME_ID));
        verify(changeMePortOut, times(1)).save(any(ChangeMeSaveCmd.class));

    }

    @Test
    @DisplayName("NotFoundException при обновление ChangeMe")
    void update_throwNotFoundException() {
        //Given

        final var cmd = ChangeMeUpdateCmd.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        when(changeMePortOut.isExists(SearchByIdRequest.of(CHANGE_ME_ID))).thenReturn(Boolean.FALSE);

        //When

        final ThrowableAssert.ThrowingCallable result = () -> sut.update(cmd);
        ;

        //Then

        assertThatCode(result)
            .isInstanceOf(NotFoundException.class);

        verify(changeMePortOut, times(1)).isExists(SearchByIdRequest.of(CHANGE_ME_ID));
        verify(changeMePortOut, never()).save(any(ChangeMeSaveCmd.class));

    }
}