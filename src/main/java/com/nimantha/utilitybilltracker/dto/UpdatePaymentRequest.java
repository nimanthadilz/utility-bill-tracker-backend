package com.nimantha.utilitybilltracker.dto;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class UpdatePaymentRequest {
    private LocalDate date;

    @Min(value = 0, message = "must be a positive value")
    private Double amount;
}
