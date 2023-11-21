package ru.nota.hexagonal.template.port.in.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChangeMeCreateCmd {
    String field1;
    String field2;
}
