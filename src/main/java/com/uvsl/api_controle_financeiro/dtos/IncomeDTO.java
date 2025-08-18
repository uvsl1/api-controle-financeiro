package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IncomeDTO(Long id,
                        String description,
                        BigDecimal amount,
                        Boolean isFixed,
                        LocalDate startDate,
                        Long userId) {
}
