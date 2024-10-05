package com.nimantha.utilitybilltracker.dto;

import com.nimantha.utilitybilltracker.validation.ValidDateRange;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ValidDateRange(message = "must be after start date")
public class CreateBillRequest {
    @NotNull
    private long utilityId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Min(value = 0, message = "must be a positive value")
    private Double amount;
}
