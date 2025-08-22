package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.dtos.MonthBalanceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
public class BalanceService {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    public MonthBalanceDTO calculateMonthBalance(Long userId, YearMonth month) {
        var incomes = incomeService.calculateMonthIncomes(month, userId);
        var expenses = expenseService.calculateMonthExpenses(month, userId);

        BigDecimal totalIncomes = incomes.total();
        BigDecimal totalExpenses = expenses.total();
        BigDecimal balance = totalIncomes.subtract(totalExpenses);

        return new MonthBalanceDTO(month, totalIncomes, totalExpenses, balance);
    }
}
