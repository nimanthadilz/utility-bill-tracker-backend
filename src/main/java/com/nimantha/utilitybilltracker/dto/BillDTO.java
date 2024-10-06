package com.nimantha.utilitybilltracker.dto;

import java.time.LocalDate;

public record BillDTO(Long utilityId, LocalDate startDate, LocalDate endDate, Double amount) {
}
