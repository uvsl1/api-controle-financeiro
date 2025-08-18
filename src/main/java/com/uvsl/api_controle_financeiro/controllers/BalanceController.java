package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.BalanceDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthBalance;
import com.uvsl.api_controle_financeiro.services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @PostMapping("/create")
    public ResponseEntity<BalanceDTO> createBalance(@RequestBody BalanceDTO balanceDTO) {
        BalanceDTO createdBalance = balanceService.createBalance(balanceDTO);
        return ResponseEntity.ok(createdBalance);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BalanceDTO>> getBalancesByUserId(@PathVariable Long userId) {
        List<BalanceDTO> balances = balanceService.getBalancesByUser(userId);
        return ResponseEntity.ok(balances);
    }

    @GetMapping("/month")
    public MonthBalance getMonthBalances(
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam Long userId) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return balanceService.calculateMonthBalances(requestedMonth, userId);
    }
}
