package com.uvsl.api_controle_financeiro.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense_installments")
public class ExpenseInstallment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Expense expense;
    private BigDecimal installmentAmount;
    private LocalDate referenceDate;

    public ExpenseInstallment() {
    }

    public ExpenseInstallment(Long id, Expense expense, BigDecimal installmentAmount, LocalDate referenceDate) {
        this.id = id;
        this.expense = expense;
        this.installmentAmount = installmentAmount;
        this.referenceDate = referenceDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public BigDecimal getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(BigDecimal installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public LocalDate getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(LocalDate referenceDate) {
        this.referenceDate = referenceDate;
    }
}
