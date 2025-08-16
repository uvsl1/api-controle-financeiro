package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.Category;
import com.uvsl.api_controle_financeiro.domain.Expense;
import com.uvsl.api_controle_financeiro.domain.PaymentMethod;
import com.uvsl.api_controle_financeiro.domain.User;
import com.uvsl.api_controle_financeiro.dtos.AllCategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.CategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.ExpenseDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthExpense;
import com.uvsl.api_controle_financeiro.repositories.CategoryRepository;
import com.uvsl.api_controle_financeiro.repositories.ExpenseRepository;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ExpenseDTO createExpense(ExpenseDTO expenseDTO) {
        Category category = categoryRepository.findByNameIgnoreCase(expenseDTO.categoryName())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        User user = userRepository.findById(expenseDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Expense expense = new Expense(
                expenseDTO.description(),
                expenseDTO.amount(),
                expenseDTO.numberOfInstallments(),
                category,
                expenseDTO.startDate(),
                expenseDTO.paymentMethod(),
                expenseDTO.fixedExpense(),
                user
        );
        Expense savedExpense = expenseRepository.save(expense);
        return toDTO(savedExpense);
    }

    public List<ExpenseDTO> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        return toDTO(expense);
    }

    public ExpenseDTO partiallyUpdateExpense(Long id, ExpenseDTO expenseDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        if (expenseDTO.description() != null) {
            expense.setDescription(expenseDTO.description());
        }
        if (expenseDTO.amount() != null) {
            expense.setAmount(expenseDTO.amount());
        }
        if (expenseDTO.numberOfInstallments() != null) {
            expense.setNumberOfInstallments(expenseDTO.numberOfInstallments());
        }
        if (expenseDTO.categoryName() != null) {
            Category category = categoryRepository.findByNameIgnoreCase(expenseDTO.categoryName())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            expense.setCategory(category);
        }
        if (expenseDTO.startDate() != null) {
            expense.setStartDate(expenseDTO.startDate());
        }
        if (expenseDTO.paymentMethod() != null) {
            expense.setPaymentMethod(expenseDTO.paymentMethod());
        }
        if (expenseDTO.fixedExpense() != null) {
            expense.setFixedExpense(expenseDTO.fixedExpense());
        }
        if (expenseDTO.userId() != null) {
            User user = userRepository.findById(expenseDTO.userId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            expense.setUser(user);
        }

        Expense updatedExpense = expenseRepository.save(expense);
        return toDTO(updatedExpense);
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        expenseRepository.delete(expense);
    }

    private ExpenseDTO toDTO(Expense expense) {
        return new ExpenseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getNumberOfInstallments(),
                expense.getCategory().getName(),
                expense.getStartDate(),
                expense.getPaymentMethod(),
                expense.isFixedExpense(),
                expense.getUser().getId()
        );
    }

    public MonthExpense calculateMonthExpenses(YearMonth month, Long userId) {
        List<Expense> allExpenses = expenseRepository.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        List<ExpenseDTO> monthExpenses = new ArrayList<>();

        for (Expense expense : allExpenses) {
            ExpenseDTO dto = toDTO(expense);
            YearMonth start = YearMonth.from(dto.startDate());

            if (dto.numberOfInstallments() != null && dto.numberOfInstallments() > 1) {
                YearMonth end = start.plusMonths(dto.numberOfInstallments() - 1);
                if (!month.isBefore(start) && !month.isAfter(end)) {
                    BigDecimal installmentValue = dto.amount()
                            .divide(BigDecimal.valueOf(dto.numberOfInstallments()));
                    total = total.add(installmentValue);
                    monthExpenses.add(dto);
                }
            }
            else if (Boolean.TRUE.equals(dto.fixedExpense()) && !month.isBefore(start)) {
                total = total.add(dto.amount());
                monthExpenses.add(dto);
            }
            else if (start.equals(month)) {
                total = total.add(dto.amount());
                monthExpenses.add(dto);
            }
        }

        return new MonthExpense(month, total, monthExpenses);
    }

    public CategoryExpenseSummaryDTO categoryExpenseSummary(Long userId, String categoryName, YearMonth month) {
        MonthExpense monthExpense = calculateMonthExpenses(month, userId);

        BigDecimal categoryTotal = monthExpense.expenses().stream()
                .filter(e -> e.categoryName().equalsIgnoreCase(categoryName))
                .map(e -> {
                    if (e.numberOfInstallments() != null && e.numberOfInstallments() > 1) {
                        return e.amount().divide(BigDecimal.valueOf(e.numberOfInstallments()), 2, RoundingMode.HALF_UP);
                    }
                    return e.amount();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthTotal = monthExpense.total();

        BigDecimal percentage = BigDecimal.ZERO;
        if (monthTotal.compareTo(BigDecimal.ZERO) > 0) {
            percentage = categoryTotal
                    .divide(monthTotal, 2, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return new CategoryExpenseSummaryDTO(categoryName, categoryTotal, percentage);
    }

    public AllCategoryExpenseSummaryDTO allCategoryExpenseSummary(Long userId, YearMonth month) {
        MonthExpense monthExpense = calculateMonthExpenses(month, userId);
        BigDecimal monthTotal = monthExpense.total();

        Map<String, BigDecimal> categoryTotals = monthExpense.expenses().stream()
                .collect(Collectors.groupingBy(
                        ExpenseDTO::categoryName,
                        Collectors.mapping(
                                e -> {
                                    if (e.numberOfInstallments() != null && e.numberOfInstallments() > 1) {
                                        return e.amount().divide(BigDecimal.valueOf(e.numberOfInstallments()), 2, RoundingMode.HALF_UP);
                                    }
                                    return e.amount();
                                },
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));

        List<CategoryExpenseSummaryDTO> categories = categoryTotals.entrySet().stream()
                .map(entry -> {
                    BigDecimal total = entry.getValue();
                    BigDecimal percentage = BigDecimal.ZERO;
                    if (monthTotal.compareTo(BigDecimal.ZERO) > 0) {
                        percentage = total
                                .divide(monthTotal, 2, RoundingMode.HALF_UP)
                                .multiply(BigDecimal.valueOf(100));
                    }
                    return new CategoryExpenseSummaryDTO(entry.getKey(), total, percentage);
                })
                .toList();

        return new AllCategoryExpenseSummaryDTO(userId, month.toString(), monthTotal, categories);
    }
}