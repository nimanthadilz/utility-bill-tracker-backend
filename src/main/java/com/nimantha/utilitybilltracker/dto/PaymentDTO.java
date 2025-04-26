package com.nimantha.utilitybilltracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private Long id;

    private LocalDate date;

    private Double amount;

    private Long billId;
}
