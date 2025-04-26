package com.nimantha.utilitybilltracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseDTO {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public ResponseDTO(String message) {
        this.message = message;
    }
}
