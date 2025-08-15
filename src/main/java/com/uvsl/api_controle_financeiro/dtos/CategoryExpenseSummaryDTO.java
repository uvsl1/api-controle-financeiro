package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;

public record CategoryExpenseSummaryDTO(BigDecimal totalAmount, BigDecimal percentageOfMonth) {
}
