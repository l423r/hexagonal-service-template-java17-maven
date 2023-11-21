package ru.nota.hexagonal.template.domain;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ChangeMe {
    UUID id;
    String field1;
    String field2;
}
