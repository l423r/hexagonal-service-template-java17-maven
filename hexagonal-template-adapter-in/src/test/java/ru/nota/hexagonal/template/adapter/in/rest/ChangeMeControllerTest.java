package ru.nota.hexagonal.template.adapter.in.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeCreateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeUpdateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.mapper.ChangeMeDTOsMapper;
import ru.nota.hexagonal.template.adapter.in.rest.mapper.ChangeMeDTOsMapperImpl;
import ru.nota.hexagonal.template.common.model.DeleteByIdCmd;
import ru.nota.hexagonal.template.common.model.PageSearchRequest;
import ru.nota.hexagonal.template.common.model.SearchByIdRequest;
import ru.nota.hexagonal.template.port.in.ChangeMeCreateUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeDeleteUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeSearchUseCase;
import ru.nota.hexagonal.template.port.in.ChangeMeUpdateUseCase;
import ru.nota.hexagonal.template.port.in.model.ChangeMeCreateCmd;
import ru.nota.hexagonal.template.port.in.model.ChangeMeResponse;
import ru.nota.hexagonal.template.port.in.model.ChangeMeUpdateCmd;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.nota.hexagonal.template.adapter.in.rest.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.adapter.in.rest.TestDataFactory.CHANGE_ME_FIELD_2;
import static ru.nota.hexagonal.template.adapter.in.rest.TestDataFactory.CHANGE_ME_ID;
import static ru.nota.hexagonal.template.adapter.in.rest.TestDataFactory.changeMeDTOBuilder;

class ChangeMeControllerTest extends AbstractControllerTest {

    private final String changeMeBaseUrl = "/api/v1/changeMe";
    @MockBean
    private ChangeMeCreateUseCase changeMeCreateUseCase;
    @MockBean
    private ChangeMeUpdateUseCase changeMeUpdateUseCase;
    @MockBean
    private ChangeMeSearchUseCase changeMeSearchUseCase;
    @MockBean
    private ChangeMeDeleteUseCase changeMeDeleteUseCase;
    @SpyBean(ChangeMeDTOsMapperImpl.class)
    private ChangeMeDTOsMapper mapper;

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание changeMe")
    void createChangeMe_success() {

        //Given
        final var changeMeCreateDTO = ChangeMeCreateDTO.builder()
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var cmd = ChangeMeCreateCmd.builder()
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        when(changeMeCreateUseCase.create(cmd)).thenReturn(CHANGE_ME_ID);

        //When

        mvc.perform(post(changeMeBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(changeMeCreateDTO)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(body(UUID.class)
                .matches(result -> assertThat(result)
                    .isNotNull()
                    .isEqualTo(CHANGE_ME_ID)));

        //Then

        verify(changeMeCreateUseCase, times(1)).create(cmd);

    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное обновление changeMe")
    void updateChangeMe() {
        //Given
        final var changeMeUpdateDTO = ChangeMeUpdateDTO.builder()
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var cmd = ChangeMeUpdateCmd.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        doNothing().when(changeMeUpdateUseCase).update(cmd);

        //When

        mvc.perform(put(changeMeBaseUrl + "/{id}", CHANGE_ME_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(changeMeUpdateDTO)))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        //Then

        verify(changeMeUpdateUseCase, times(1)).update(cmd);
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешный поиск по ИД changeMe")
    void searchChangeMeById() {
        //Given
        final var changeMeResponse = ChangeMeResponse.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var changeMeDTO = changeMeDTOBuilder()
            .build();

        final var request = SearchByIdRequest.of(CHANGE_ME_ID);

        when(changeMeSearchUseCase.searchById(request)).thenReturn(changeMeResponse);

        //When

        mvc.perform(get(changeMeBaseUrl + "/{id}", CHANGE_ME_ID)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(body(ChangeMeDTO.class)
                .matches(result -> assertThat(result)
                    .isNotNull()
                    .isEqualTo(changeMeDTO)));

        //Then

        verify(changeMeSearchUseCase, times(1)).searchById(request);
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешный постраничный поиск changeMe")
    void searchChangeMe() {
        //Given
        final var changeMeResponse = ChangeMeResponse.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var changeMeResponses = new PageImpl<>(List.of(changeMeResponse));

        final var changeMeDTO = changeMeDTOBuilder()
            .build();

        final var changeMeDTOS = new ControllerTestConfig.CustomPageImpl<>(List.of(changeMeDTO));

        final var request = PageSearchRequest.builder().build();

        when(changeMeSearchUseCase.searchAll(request)).thenReturn(changeMeResponses);

        //When

        mvc.perform(get(changeMeBaseUrl)
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(body(new TypeReference<ControllerTestConfig.CustomPageImpl<ChangeMeDTO>>() {
            })
                .matches(result -> assertThat(result)
                    .isNotNull()
                    .isEqualTo(changeMeDTOS)));

        //Then

        verify(changeMeSearchUseCase, times(1)).searchAll(request);
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное удаление changeMe")
    void deleteChangeMe() {
        //Given

        final var cmd = DeleteByIdCmd.of(CHANGE_ME_ID);

        doNothing().when(changeMeDeleteUseCase).deleteById(cmd);

        //When

        mvc.perform(delete(changeMeBaseUrl + "/{id}", CHANGE_ME_ID))
            .andDo(print())
            .andExpect(status().is2xxSuccessful());

        //Then

        verify(changeMeDeleteUseCase, times(1)).deleteById(cmd);
    }
}