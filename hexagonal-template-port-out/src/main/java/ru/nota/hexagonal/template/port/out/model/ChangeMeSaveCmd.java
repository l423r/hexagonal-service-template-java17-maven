package ru.nota.hexagonal.template.port.out.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ChangeMeSaveCmd {
    UUID id;
    String field1;
    String field2;
}
