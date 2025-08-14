package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record MonthExpense(YearMonth month, BigDecimal total, List<ExpenseDTO> expenses) {
}
