package ru.nota.hexagonal.template;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = AbstractIntegrationTest.DockerPostgresDataSourceInitializer.class)
public abstract class AbstractIntegrationTest {
    public static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:13.3");

    static {
        postgresDBContainer.start();
    }

    @Autowired
    protected TestDataCreator dataCreator;
    @Autowired
    protected TestDataCleaner dataCleaner;
    @Autowired
    protected ObjectMapper mapper;

    public static class DockerPostgresDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + postgresDBContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgresDBContainer.getUsername(),
                "spring.datasource.password=" + postgresDBContainer.getPassword()
            );
        }
    }

    @EqualsAndHashCode(callSuper = false)
    public static class CustomPageImpl<T> extends PageImpl<T> {
        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public CustomPageImpl(@JsonProperty("content") List<T> content, @JsonProperty("number") int number,
                              @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements,
                              @JsonProperty("pageable") JsonNode pageable, @JsonProperty("last") boolean last,
                              @JsonProperty("totalPages") int totalPages, @JsonProperty("sort") JsonNode sort,
                              @JsonProperty("numberOfElements") int numberOfElements) {
            super(content, PageRequest.of(number, size), totalElements);
        }

        public CustomPageImpl(List<T> content, Pageable pageable, long total) {
            super(content, pageable, total);
        }

        public CustomPageImpl(List<T> content) {
            super(content);
        }

        public CustomPageImpl() {
            super(new ArrayList<>());
        }
    }

}
