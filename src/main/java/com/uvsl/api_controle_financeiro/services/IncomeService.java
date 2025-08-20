package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.Income;
import com.uvsl.api_controle_financeiro.dtos.IncomeDTO;
import com.uvsl.api_controle_financeiro.dtos.MonthIncomes;
import com.uvsl.api_controle_financeiro.repositories.IncomeRepository;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserRepository userRepository;

    private IncomeDTO toDTO(Income income) {
        return new IncomeDTO(
                income.getId(),
                income.getDescription(),
                income.getAmount(),
                income.getFixed(),
                income.getStartDate(),
                income.getUser().getId()
        );
    }

    public IncomeDTO createIncome(IncomeDTO incomeDTO) {
        var user = userRepository.findById(incomeDTO.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Income income = new Income(
                incomeDTO.description(),
                incomeDTO.amount(),
                incomeDTO.isFixed(),
                incomeDTO.startDate(),
                user
        );
        Income savedIncome = incomeRepository.save(income);
        return toDTO(savedIncome);
    }

    public List<IncomeDTO> getIncomesByUser(Long userId) {
        return incomeRepository.findByUserId(userId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public MonthIncomes calculateMonthIncomes(YearMonth month, Long userId) {
        List<Income> allIncomes = incomeRepository.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;
        List<IncomeDTO> monthIncomes = new ArrayList<>();

        for (Income income : allIncomes) {
            YearMonth start = YearMonth.from(income.getStartDate());

            if (Boolean.TRUE.equals(income.getFixed()) && !month.isBefore(start)) {
                total = total.add(income.getAmount());
                monthIncomes.add(toDTO(income));
            } else if (start.equals(month)) {
                total = total.add(income.getAmount());
                monthIncomes.add(toDTO(income));
            }
        }

        return new MonthIncomes(month, total, monthIncomes);
    }

    public IncomeDTO partiallyUpdateIncome(Long id, IncomeDTO incomeDTO) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        if (incomeDTO.description() != null) {
            income.setDescription(incomeDTO.description());
        }
        if (incomeDTO.amount() != null) {
            income.setAmount(incomeDTO.amount());
        }
        if (incomeDTO.isFixed() != null) {
            income.setFixed(incomeDTO.isFixed());
        }
        if (incomeDTO.startDate() != null) {
            income.setStartDate(incomeDTO.startDate());
        }
        if (incomeDTO.userId() != null) {
            var user = userRepository.findById(incomeDTO.userId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            income.setUser(user);
        }

        Income updatedIncome = incomeRepository.save(income);
        return toDTO(updatedIncome);
    }

    public void deleteIncome(Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
        incomeRepository.delete(income);
    }
}
