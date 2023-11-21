package ru.nota.hexagonal.template.port.in.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ChangeMeUpdateCmd {
    UUID id;
    String field1;
    String field2;
}
