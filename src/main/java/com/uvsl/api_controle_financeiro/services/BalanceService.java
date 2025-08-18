package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.Balance;
import com.uvsl.api_controle_financeiro.dtos.BalanceDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthBalance;
import com.uvsl.api_controle_financeiro.repositories.BalanceRepository;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private UserRepository userRepository;

    private BalanceDTO toDTO(Balance balance) {
        return new BalanceDTO(
                balance.getId(),
                balance.getDescription(),
                balance.getAmount(),
                balance.getFixed(),
                balance.getStartDate(),
                balance.getUser().getId()
        );
    }

    public BalanceDTO createBalance(BalanceDTO balanceDTO) {
        var user = userRepository.findById(balanceDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Balance balance = new Balance(
                balanceDTO.description(),
                balanceDTO.amount(),
                balanceDTO.isFixed(),
                balanceDTO.startDate(),
                user
        );
        Balance savedBalance = balanceRepository.save(balance);
        return toDTO(savedBalance);
    }

    public List<BalanceDTO> getBalancesByUser(Long userId) {
        return balanceRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public MonthBalance calculateMonthBalances(YearMonth month, Long userId) {
        List<Balance> allBalances = balanceRepository.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        List<BalanceDTO> monthBalances = new ArrayList<>();

        for (Balance balance : allBalances) {
            YearMonth start = YearMonth.from(balance.getStartDate());

            if (Boolean.TRUE.equals(balance.getFixed()) && !month.isBefore(start)) {
                total = total.add(balance.getAmount());
                monthBalances.add(toDTO(balance));
            } else if (start.equals(month)) {
                total = total.add(balance.getAmount());
                monthBalances.add(toDTO(balance));
            }
        }

        return new MonthBalance(month, total, monthBalances);
    }
}
