package com.nimantha.utilitybilltracker.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class CreateUtilityRequest {

    @NotNull
    private String name;

    @NotNull
    private String accountNo;
}
