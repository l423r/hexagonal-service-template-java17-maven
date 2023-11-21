package ru.nota.hexagonal.template;

import lombok.experimental.UtilityClass;
import ru.nota.hexagonal.template.adapter.out.jpa.entity.ChangeMeEntity;

import java.util.UUID;

@UtilityClass
public class TestDataFactory {
    public static final UUID CHANGE_ME_ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    public static final String CHANGE_ME_FIELD_1 = "field1";
    public static final String CHANGE_ME_FIELD_2 = "field2";

    public static ChangeMeEntity.ChangeMeEntityBuilder getChangeMeEntityBuilder() {
        return ChangeMeEntity.builder()
            .id(CHANGE_ME_ID)
            .field1(CHANGE_ME_FIELD_1)
            .field2(CHANGE_ME_FIELD_2);
    }
}
