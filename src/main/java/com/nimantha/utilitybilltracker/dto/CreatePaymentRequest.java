package com.nimantha.utilitybilltracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class CreatePaymentRequest {
    @NotNull
    private Long billId;

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(value = 0, message = "must be a positive value")
    private Double amount;
}
