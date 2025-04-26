package com.nimantha.utilitybilltracker.dto;

import java.time.LocalDate;

public record BillDTO(Long id, LocalDate startDate, LocalDate endDate, Double amount, Long utilityId) {
}
