package ru.nota.hexagonal.template.common.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(staticName = "of")
public class SearchByParamRequest<T> {
    T param;
}
