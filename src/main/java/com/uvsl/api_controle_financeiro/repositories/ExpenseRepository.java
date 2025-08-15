package com.uvsl.api_controle_financeiro.repositories;

import com.uvsl.api_controle_financeiro.domain.Expense;
import com.uvsl.api_controle_financeiro.dtos.ExpenseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId);
    List<Expense> findByUserIdAndCategoryNameIgnoreCase(Long userId, String categoryName);
}
