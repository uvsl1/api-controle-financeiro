package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.IncomeDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthIncomes;
import com.uvsl.api_controle_financeiro.security.UserDetailsImpl;
import com.uvsl.api_controle_financeiro.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/create")
    public ResponseEntity<IncomeDTO> createIncome(
            @RequestBody IncomeDTO incomeDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long userId = userDetails.getId();
        IncomeDTO createdIncome = incomeService.createIncome(incomeDTO, userId);
        return ResponseEntity.ok(createdIncome);
    }

    @GetMapping("/me")
    public ResponseEntity<List<IncomeDTO>> getMyIncomes(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<IncomeDTO> incomes = incomeService.getIncomesByUser(userDetails.getId());
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/month")
    public MonthIncomes getMonthIncomes(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return incomeService.calculateMonthIncomes(requestedMonth, userDetails.getId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IncomeDTO> partiallyUpdateIncome(
            @PathVariable Long id,
            @RequestBody IncomeDTO incomeDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        IncomeDTO updatedIncome = incomeService.partiallyUpdateIncome(id, incomeDTO, userDetails.getId());
        return ResponseEntity.ok(updatedIncome);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        incomeService.deleteIncome(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
