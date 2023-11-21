package ru.nota.hexagonal.template.application;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_ID;

@ExtendWith(MockitoExtension.class)
class ChangeMeDeleteApplicationServiceTest {

    @Mock
    private ChangeMePortOut changeMePortOut;

    @InjectMocks
    private ChangeMeDeleteApplicationService sut;

    @Test
    @DisplayName("Успешное удаление ChangeMe")
    void deleteById_success() {
        //Given

        final var cmd = DeleteByIdCmd.of(CHANGE_ME_ID);

        doNothing().when(changeMePortOut).deleteById(cmd);

        //When

        final ThrowableAssert.ThrowingCallable result = () -> sut.deleteById(cmd);

        //Then

        assertThatCode(result)
            .doesNotThrowAnyException();

        verify(changeMePortOut, times(1)).deleteById(cmd);
    }
}