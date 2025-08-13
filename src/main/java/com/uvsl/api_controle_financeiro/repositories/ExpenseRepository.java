package com.uvsl.api_controle_financeiro.repositories;

import com.uvsl.api_controle_financeiro.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
