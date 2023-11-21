package ru.nota.hexagonal.template.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class ChangeMeDTO {
    UUID id;
    String field1;
    String field2;
}
