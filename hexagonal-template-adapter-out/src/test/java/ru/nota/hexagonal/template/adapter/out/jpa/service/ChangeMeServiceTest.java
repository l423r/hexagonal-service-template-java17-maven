package ru.nota.hexagonal.template.adapter.out.jpa.service;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.nota.hexagonal.template.adapter.out.jpa.mapper.ChangeMeEntityMapper;
import ru.nota.hexagonal.template.adapter.out.jpa.mapper.ChangeMeEntityMapperImpl;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.port.out.model.ChangeMeSaveCmd;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.nota.hexagonal.template.adapter.out.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.adapter.out.TestDataFactory.CHANGE_ME_FIELD_2;
import static ru.nota.hexagonal.template.adapter.out.TestDataFactory.CHANGE_ME_ID;
import static ru.nota.hexagonal.template.adapter.out.TestDataFactory.getChangeMeEntityBuilder;

@ExtendWith(MockitoExtension.class)
class ChangeMeServiceTest {

    @Spy
    private ChangeMeEntityMapper mapper = new ChangeMeEntityMapperImpl();

    @Mock
    private ChangeMeRepository repository;

    @InjectMocks
    private ChangeMeService sut;

    @Test
    @DisplayName("Успешное сохранение ChangeMe")
    void save_success() {
        //Given

        final var cmd = ChangeMeSaveCmd.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var entity = getChangeMeEntityBuilder()
            .build();

        when(repository.save(entity)).thenReturn(entity);

        //When

        final var result = sut.save(cmd);

        //Then

        assertThat(result)
            .isNotNull()
            .hasNoNullFieldsOrProperties();

        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toDomainModel(entity);

    }

    @Test
    @DisplayName("Успешный поиск ChangeMe по ид")
    void findById_success() {
        //Given

        final var request = SearchByIdRequest.of(CHANGE_ME_ID);

        final var entity = getChangeMeEntityBuilder()
            .build();

        when(repository.findById(CHANGE_ME_ID)).thenReturn(Optional.of(entity));

        //When

        final var result = sut.findById(request);

        //Then

        assertThat(result)
            .isPresent()
            .get()
            .hasNoNullFieldsOrProperties();

        verify(repository, times(1)).findById(CHANGE_ME_ID);
        verify(mapper, times(1)).toDomainModel(entity);
    }

    @Test
    @DisplayName("Успешный постраничный поиск всех ChangeMe")
    void findAll_success() {
        //Given

        final var request = PageSearchRequest.builder()
            .build();

        final var entity = getChangeMeEntityBuilder()
            .build();

        final var pageRequest = PageRequest.of(request.getPage(), request.getPageSize(), Sort.by(request.getSortDirection(), request.getSortField()));

        final var page = new PageImpl<>(List.of(entity));

        when(repository.findAll(pageRequest)).thenReturn(page);

        //When

        final var result = sut.findAll(request);

        //Then

        assertThat(result.getContent())
            .isNotEmpty()
            .asList()
            .first()
            .hasNoNullFieldsOrProperties();

        verify(repository, times(1)).findAll(pageRequest);
        verify(mapper, times(1)).toDomainModel(entity);

    }

    @Test
    @DisplayName("Успешное удаление ChangeMe по ид")
    void deleteById_success() {
        //Given

        final var cmd = DeleteByIdCmd.of(CHANGE_ME_ID);

        doNothing().when(repository).deleteById(CHANGE_ME_ID);

        //When

        final ThrowableAssert.ThrowingCallable execution = () -> sut.deleteById(cmd);

        //Then

        assertThatCode(execution)
            .doesNotThrowAnyException();

        verify(repository, times(1)).deleteById(CHANGE_ME_ID);

    }

    @Test
    @DisplayName("Успешная проверка существования ChangeMe")
    void isExists_success() {
        //Given

        final var request = SearchByIdRequest.of(CHANGE_ME_ID);

        when(repository.existsById(CHANGE_ME_ID)).thenReturn(true);

        //When

        final var result = sut.isExists(request);

        //Then

        assertThat(result)
            .isTrue();

        verify(repository, times(1)).existsById(CHANGE_ME_ID);
    }
}