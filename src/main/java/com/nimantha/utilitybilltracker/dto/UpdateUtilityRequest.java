package com.nimantha.utilitybilltracker.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UpdateUtilityRequest {

    private String name;

    private String accountNo;
}
