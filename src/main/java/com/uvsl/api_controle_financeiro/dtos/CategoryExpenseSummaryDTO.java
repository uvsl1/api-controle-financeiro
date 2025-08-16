package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;

public record CategoryExpenseSummaryDTO(String categoryName, BigDecimal totalAmount, BigDecimal percentageOfMonth) {
}
