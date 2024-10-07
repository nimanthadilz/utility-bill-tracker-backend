package com.nimantha.utilitybilltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
public class PaymentDTO {
    private Long id;

    private LocalDate date;

    private Double amount;

    private Long billId;
}
