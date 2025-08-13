package com.uvsl.api_controle_financeiro.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal amount;

    private Integer numberOfInstallments;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Boolean fixedExpense;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense() {
    }

    public Expense(String description, BigDecimal amount, int numberOfInstallments, Category category, LocalDate startDate, PaymentMethod paymentMethod, boolean fixedExpense, User user) {
        this.description = description;
        this.amount = amount;
        this.numberOfInstallments = numberOfInstallments;
        this.category = category;
        this.startDate = startDate;
        this.paymentMethod = paymentMethod;
        this.fixedExpense = fixedExpense;
        this.user = user;
    }

    public Expense(Long id, String description, BigDecimal amount, int numberOfInstallments, Category category, LocalDate startDate, PaymentMethod paymentMethod, boolean fixedExpense, User user) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.numberOfInstallments = numberOfInstallments;
        this.category = category;
        this.startDate = startDate;
        this.paymentMethod = paymentMethod;
        this.fixedExpense = fixedExpense;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isFixedExpense() {
        return fixedExpense;
    }

    public void setFixedExpense(boolean fixedExpense) {
        this.fixedExpense = fixedExpense;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
