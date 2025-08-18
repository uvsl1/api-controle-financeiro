package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.IncomeDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthIncomes;
import com.uvsl.api_controle_financeiro.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping("/create")
    public ResponseEntity<IncomeDTO> createBalance(@RequestBody IncomeDTO incomeDTO) {
        IncomeDTO createdIncome = incomeService.createIncome(incomeDTO);
        return ResponseEntity.ok(createdIncome);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IncomeDTO>> getIncomesByUserId(@PathVariable Long userId) {
        List<IncomeDTO> incomes = incomeService.getIncomesByUser(userId);
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/month")
    public MonthIncomes getMonthIncomes(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam Long userId) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return incomeService.calculateMonthIncomes(requestedMonth, userId);
    }
}
