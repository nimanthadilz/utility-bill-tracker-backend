package com.nimantha.utilitybilltracker.dto;

import com.nimantha.utilitybilltracker.validation.ValidDateRange;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ValidDateRange(message = "must be after start date")
public class UpdateBillRequest {
    private LocalDate startDate;

    private LocalDate endDate;

    @Min(value = 0, message = "must be a positive value")
    private Double amount;
}
