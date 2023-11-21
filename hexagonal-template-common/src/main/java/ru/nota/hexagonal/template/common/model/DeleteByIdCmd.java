package ru.nota.hexagonal.template.common.model;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@RequiredArgsConstructor(staticName = "of")
public class DeleteByIdCmd implements HasId {
    UUID id;
}
