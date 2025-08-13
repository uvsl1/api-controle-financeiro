package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.uvsl.api_controle_financeiro.domain.PaymentMethod;

public record ExpenseDTO(Long id,
                         String description,
                         BigDecimal amount,
                         Integer numberOfInstallments,
                         Long categoryId,
                         LocalDate startDate,
                         PaymentMethod paymentMethod,
                         Boolean fixedExpense,
                         Long userId) {
}
