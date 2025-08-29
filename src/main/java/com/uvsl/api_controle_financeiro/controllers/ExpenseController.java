package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.AllCategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.CategoryExpenseSummaryDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthExpense;
import com.uvsl.api_controle_financeiro.security.UserDetailsImpl;
import com.uvsl.api_controle_financeiro.services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<ExpenseDTO> createExpense(
            @RequestBody ExpenseDTO expenseDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getId();
        ExpenseDTO createdExpense = expenseService.createExpense(expenseDTO, userId);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ExpenseDTO>> getMyExpenses(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getId();
        List<ExpenseDTO> expenses = expenseService.getExpensesByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpenseById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id, userDetails.getId());
        return ResponseEntity.ok(expenseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> partiallyUpdateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseDTO expenseDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        ExpenseDTO updatedExpense = expenseService.partiallyUpdateExpense(id, expenseDTO, userDetails.getId());
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        expenseService.deleteExpense(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/month")
    public MonthExpense getMonthExpenses(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return expenseService.calculateMonthExpenses(requestedMonth, userDetails.getId());
    }

    @GetMapping("/category-summary")
    public CategoryExpenseSummaryDTO getCategorySummary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam String categoryName,
            @RequestParam int year,
            @RequestParam int month) {
        return expenseService.categoryExpenseSummary(userDetails.getId(), categoryName, YearMonth.of(year, month));
    }

    @GetMapping("/all-category-summary")
    public AllCategoryExpenseSummaryDTO allCategoryExpenseSummary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam int year,
            @RequestParam int month) {
        return expenseService.allCategoryExpenseSummary(userDetails.getId(), YearMonth.of(year, month));
    }
}

