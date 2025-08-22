package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.YearMonth;

public record MonthBalanceDTO(
        YearMonth month,
        BigDecimal totalIncomes,
        BigDecimal totalExpenses,
        BigDecimal balance
) {}
