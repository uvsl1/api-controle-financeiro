package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record MonthIncomes(YearMonth month, BigDecimal total, List<IncomeDTO> balances) {
}
