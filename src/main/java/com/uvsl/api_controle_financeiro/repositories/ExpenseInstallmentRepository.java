package com.uvsl.api_controle_financeiro.repositories;

import com.uvsl.api_controle_financeiro.domain.ExpenseInstallment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseInstallmentRepository extends JpaRepository<ExpenseInstallment, Long> {
}
