package ru.nota.hexagonal.template;

import lombok.SneakyThrows;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeCreateDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeDTO;
import ru.nota.hexagonal.template.adapter.in.rest.dto.ChangeMeUpdateDTO;
import ru.nota.hexagonal.template.adapter.out.jpa.repository.ChangeMeRepository;

import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.nota.hexagonal.template.TestDataFactory.CHANGE_ME_FIELD_1;
import static ru.nota.hexagonal.template.TestDataFactory.CHANGE_ME_FIELD_2;

class ChangeMeRESTIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ChangeMeRepository changeMeRepository;

    @AfterEach
    void clean() {
        dataCleaner.changeMeTruncate();
    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное создание changeMe")
    void create_success() {
        //
        // Given
        //

        final var changeMeCreateDTO = ChangeMeCreateDTO.builder()
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2)
            .build();

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var request = new HttpEntity<>(changeMeCreateDTO, headers);

        //
        // When
        //

        final var response = restTemplate.postForEntity(
            "/api/v1/changeMe", request, UUID.class);

        //
        // Then
        //

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.CREATED);

        final var result = changeMeRepository.findById(Objects.requireNonNull(response.getBody()));

        assertThat(result)
            .isPresent()
            .get()
            .has(new Condition<>(r -> r.getId().equals(response.getBody()), "Has correct id"))
            .has(new Condition<>(r -> r.getField1().equals(CHANGE_ME_FIELD_1), "Has correct field1"))
            .has(new Condition<>(r -> r.getField2().equals(CHANGE_ME_FIELD_2), "Has correct field2"));

    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное обновление changeMe")
    void update_success() {
        //
        // Given
        //

        final var changeMeEntity = dataCreator.changeMe();

        final var changedField1 = "changedField1";
        final var changedField2 = "changedField2";
        final var changeMeUpdateDTO = ChangeMeUpdateDTO.builder()
            .field1(changedField1)
            .field2(changedField2)
            .build();

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var request = new HttpEntity<>(changeMeUpdateDTO, headers);
        final var id = changeMeEntity.getId();

        //
        // When
        //

        final var response = restTemplate.exchange("/api/v1/changeMe/{id}", HttpMethod.PUT, request, Void.class, id);

        //
        // Then
        //

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);

        final var result = changeMeRepository.findById(Objects.requireNonNull(id));

        assertThat(result)
            .isPresent()
            .get()
            .has(new Condition<>(r -> r.getField1().equals(changedField1), "Has correct field1"))
            .has(new Condition<>(r -> r.getField2().equals(changedField2), "Has correct field2"));

    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное удаление changeMe")
    void delete_success() {
        //
        // Given
        //

        final var changeMeEntity = dataCreator.changeMe();

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var request = new HttpEntity<>(headers);
        final var id = changeMeEntity.getId();

        //
        // When
        //

        final var response = restTemplate.exchange("/api/v1/changeMe/{id}", HttpMethod.DELETE, request, Void.class, id);

        //
        // Then
        //

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.NO_CONTENT);

        final var result = changeMeRepository.findById(Objects.requireNonNull(id));

        assertThat(result)
            .isEmpty();

    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное получение changeMe по Id")
    void searchById_success() {
        //
        // Given
        //

        final var changeMeEntity = dataCreator.changeMe();

        final var id = changeMeEntity.getId();
        //
        // When
        //

        final var response = restTemplate.getForEntity(
            "/api/v1/changeMe/{id}", ChangeMeDTO.class, id);

        //
        // Then
        //

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasNoNullFieldsOrProperties();

    }

    @Test
    @SneakyThrows
    @DisplayName("Успешное постраничное получение changeMe")
    void searchAll_success() {
        //
        // Given
        //

        dataCreator.changeMe();

        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final var request = new HttpEntity<>(headers);

        //
        // When
        //

        final var response = restTemplate.exchange("/api/v1/changeMe?page=0&pageSize=10&sort=field1", HttpMethod.GET, request,
            new ParameterizedTypeReference<CustomPageImpl<ChangeMeDTO>>() {
            });

        //
        // Then
        //

        assertThat(response.getStatusCode())
            .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
            .isNotNull()
            .hasNoNullFieldsOrProperties();

        assertThat(response.getBody().getContent())
            .asList()
            .first()
            .hasNoNullFieldsOrProperties();

    }
}
