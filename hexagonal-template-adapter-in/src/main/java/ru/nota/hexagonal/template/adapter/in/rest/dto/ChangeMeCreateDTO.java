package ru.nota.hexagonal.template.adapter.in.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = PRIVATE)
public class ChangeMeCreateDTO {
    String field1;
    String field2;
}
