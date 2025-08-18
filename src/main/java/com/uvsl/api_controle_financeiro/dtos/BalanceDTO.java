package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BalanceDTO(Long id,
                         String description,
                         BigDecimal amount,
                         Boolean isFixed,
                         LocalDate startDate,
                         Long userId) {
}
