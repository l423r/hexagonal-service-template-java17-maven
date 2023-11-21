package ru.nota.hexagonal.template.adapter.in.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static java.nio.charset.StandardCharsets.UTF_8;

@ActiveProfiles("test")
@Import(value = {ControllerTestConfig.class})
@WebMvcTest(controllers = {ChangeMeController.class})
public abstract class AbstractControllerTest {
    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected <T> DeserializedBodyResultMatcherBuilder<T> body(Class<T> bodyType) {
        return new DeserializedBodyResultMatcherBuilder<>(mvcResult -> {
            try {
                return objectMapper.readValue(mvcResult.getResponse().getContentAsString(UTF_8), bodyType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    protected <T> DeserializedBodyResultMatcherBuilder<T> body(TypeReference<T> responseType) {
        return new DeserializedBodyResultMatcherBuilder<>(mvcResult -> {
            try {
                return objectMapper.readValue(mvcResult.getResponse().getContentAsString(UTF_8), responseType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
