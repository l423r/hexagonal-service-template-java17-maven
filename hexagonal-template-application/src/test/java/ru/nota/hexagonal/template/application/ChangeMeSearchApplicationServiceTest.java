package ru.nota.hexagonal.template.application;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import ru.nota.hexagonal.template.application.mapper.ChangeMeResponseMapper;
import ru.nota.hexagonal.template.application.mapper.ChangeMeResponseMapperImpl;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.domain.ChangeMe;
import ru.nota.hexagonal.template.exception.NotFoundException;
import ru.nota.hexagonal.template.port.out.ChangeMePortOut;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_FIELD_2;
import static ru.nota.hexagonal.template.application.TestDataFactory.CHANGE_ME_ID;

@ExtendWith(MockitoExtension.class)
class ChangeMeSearchApplicationServiceTest {

    @Mock
    private ChangeMePortOut changeMePortOut;

    @Spy
    private ChangeMeResponseMapper mapper = new ChangeMeResponseMapperImpl();

    @InjectMocks
    private ChangeMeSearchApplicationService sut;

    @Test
    @DisplayName("Успешный постраничный поиск ChangeMe")
    void searchAll_success() {
        //Given

        final var request = PageSearchRequest.builder()
            .build();

        final var changeMe = ChangeMe.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var page = new PageImpl<>(List.of(changeMe));

        when(changeMePortOut.findAll(request)).thenReturn(page);

        //When

        final var result = sut.searchAll(request);

        //Then

        assertThat(result.getContent())
            .isNotEmpty()
            .asList()
            .first()
            .hasNoNullFieldsOrProperties();

        verify(changeMePortOut, times(1)).findAll(request);
        verify(mapper, times(1)).toResponse(changeMe);
    }

    @Test
    @DisplayName("Успешный поиск по ид ChangeMe")
    void searchById_success() {
        //Given

        final var request = SearchByIdRequest.of(CHANGE_ME_ID);

        final var changeMe = ChangeMe.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        when(changeMePortOut.findById(request)).thenReturn(Optional.of(changeMe));

        //When

        final var result = sut.searchById(request);

        //Then

        assertThat(result)
            .isNotNull()
            .hasNoNullFieldsOrProperties();

        verify(changeMePortOut, times(1)).findById(request);
        verify(mapper, times(1)).toResponse(changeMe);

    }

    @Test
    @DisplayName("NotFoundException при поиске по ид ChangeMe")
    void searchById_throwNotFoundException() {
        //Given

        final var request = SearchByIdRequest.of(CHANGE_ME_ID);

        when(changeMePortOut.findById(request)).thenReturn(Optional.empty());

        //When

        final ThrowableAssert.ThrowingCallable result = () -> sut.searchById(request);

        //Then

        assertThatCode(result)
            .isInstanceOf(NotFoundException.class);

        verify(changeMePortOut, times(1)).findById(request);

    }
}