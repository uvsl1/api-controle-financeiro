package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.AllCategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.CategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthExpense;
import com.uvsl.api_controle_financeiro.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uvsl.api_controle_financeiro.dtos.ExpenseDTO;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/create")
    public ResponseEntity<ExpenseDTO> createExpense(@RequestBody ExpenseDTO expenseDTO) {
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDTO);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseDTO>> getExpensesByUserId(@PathVariable Long userId) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expenseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> partiallyUpdateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseDTO expenseDTO) {

        ExpenseDTO updatedExpense = expenseService.partiallyUpdateExpense(id, expenseDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/month")
    public MonthExpense getMonthExpenses(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam Long userId) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return expenseService.calculateMonthExpenses(requestedMonth, userId);
    }

    @GetMapping("/category-summary")
    public CategoryExpenseSummaryDTO getCategorySummary(
            @RequestParam Long userId,
            @RequestParam String categoryName,
            @RequestParam int year,
            @RequestParam int month) {
        return expenseService.categoryExpenseSummary(userId, categoryName, YearMonth.of(year, month));
    }

    @GetMapping("/all-category-summary")
    public AllCategoryExpenseSummaryDTO allCategoryExpenseSummary(
            @RequestParam Long userId,
            @RequestParam int year,
            @RequestParam int month) {
        return expenseService.allCategoryExpenseSummary(userId, YearMonth.of(year, month));
    }
}

