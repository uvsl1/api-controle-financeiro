package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.Category;
import com.uvsl.api_controle_financeiro.domain.Expense;
import com.uvsl.api_controle_financeiro.domain.PaymentMethod;
import com.uvsl.api_controle_financeiro.domain.User;
import com.uvsl.api_controle_financeiro.dtos.ExpenseDTO;
import com.uvsl.api_controle_financeiro.repositories.CategoryRepository;
import com.uvsl.api_controle_financeiro.repositories.ExpenseRepository;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Category category = categoryRepository.findById(expenseDTO.categoryId())
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
        return new ExpenseDTO(
                savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getAmount(),
                savedExpense.getNumberOfInstallments(),
                savedExpense.getCategory().getId(),
                savedExpense.getStartDate(),
                savedExpense.getPaymentMethod(),
                savedExpense.isFixedExpense(),
                savedExpense.getUser().getId()
        );
    }

    public List<ExpenseDTO> getExpensesByUserId(Long userId) {
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        return expenses.stream()
                .map(expense -> new ExpenseDTO(
                        expense.getId(),
                        expense.getDescription(),
                        expense.getAmount(),
                        expense.getNumberOfInstallments(),
                        expense.getCategory().getId(),
                        expense.getStartDate(),
                        expense.getPaymentMethod(),
                        expense.isFixedExpense(),
                        expense.getUser().getId()
                ))
                .collect(Collectors.toList());
    }

    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        return new ExpenseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getNumberOfInstallments(),
                expense.getCategory().getId(),
                expense.getStartDate(),
                expense.getPaymentMethod(),
                expense.isFixedExpense(),
                expense.getUser().getId()
        );
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
        if (expenseDTO.categoryId() != null) {
            Category category = categoryRepository.findById(expenseDTO.categoryId())
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

        return new ExpenseDTO(
                updatedExpense.getId(),
                updatedExpense.getDescription(),
                updatedExpense.getAmount(),
                updatedExpense.getNumberOfInstallments(),
                updatedExpense.getCategory().getId(),
                updatedExpense.getStartDate(),
                updatedExpense.getPaymentMethod(),
                updatedExpense.isFixedExpense(),
                updatedExpense.getUser().getId()
        );
    }

    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
        expenseRepository.delete(expense);
    }
}