package ru.nota.hexagonal.template.adapter.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Sort;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ChangeMeSearchQueryDTO {

    @NotNull
    Integer pageNumber;
    @NotNull
    Integer pageSize;
    @NotBlank
    String sort;
    @Builder.Default
    Sort.Direction sortDirection = Sort.Direction.ASC;

}
