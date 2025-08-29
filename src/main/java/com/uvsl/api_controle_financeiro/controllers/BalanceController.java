package com.uvsl.api_controle_financeiro.controllers;

import com.uvsl.api_controle_financeiro.dtos.MonthBalanceDTO;
import com.uvsl.api_controle_financeiro.security.UserDetailsImpl;
import com.uvsl.api_controle_financeiro.services.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/balances")
public class BalanceController {

    @Autowired
    private BalanceService balanceService;

    @GetMapping("/month")
    public MonthBalanceDTO getMonthBalance(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        YearMonth requestedMonth = YearMonth.of(year, month);
        return balanceService.calculateMonthBalance(userDetails.getId(), requestedMonth);
    }
}
