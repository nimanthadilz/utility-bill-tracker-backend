package com.nimantha.utilitybilltracker.dto;

import java.time.LocalDate;

public record CreateBillDTO(String username, Long utilityId, LocalDate startDate, LocalDate endDate, double amount) {
}
