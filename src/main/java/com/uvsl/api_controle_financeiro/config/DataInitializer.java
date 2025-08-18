package com.uvsl.api_controle_financeiro.config;

import com.uvsl.api_controle_financeiro.domain.*;
import com.uvsl.api_controle_financeiro.repositories.BalanceRepository;
import com.uvsl.api_controle_financeiro.repositories.CategoryRepository;
import com.uvsl.api_controle_financeiro.repositories.ExpenseRepository;
import com.uvsl.api_controle_financeiro.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository,
                                   UserRepository userRepository,
                                   ExpenseRepository expenseRepository,
                                   BalanceRepository balanceRepository) {
        return args -> {

            // Categorias fixas

            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category("Moradia"));
                categoryRepository.save(new Category("Contas"));
                categoryRepository.save(new Category("Necessidades"));
                categoryRepository.save(new Category("Ifood/Restaurantes"));
                categoryRepository.save(new Category("Uber/Transporte"));
                categoryRepository.save(new Category("Saúde"));
                categoryRepository.save(new Category("Lazer"));
                categoryRepository.save(new Category("Eletrônicos"));
                categoryRepository.save(new Category("Assinaturas"));
                categoryRepository.save(new Category("Roupas"));
                categoryRepository.save(new Category("Beleza"));
                categoryRepository.save(new Category("Presentes"));
                categoryRepository.save(new Category("Investimentos"));
                categoryRepository.save(new Category("Outros"));
            }

            // Usuario de teste

            if (userRepository.count() == 0) {
                User user = new User();
                user.setName("Ugo V");
                user.setEmail("ugo.com");
                user.setPassword("123");
                userRepository.save(user);
            }

            // Despesas de teste
            User user = userRepository.findAll().get(0);

            if (expenseRepository.count() == 0) {
                Category categoryMoradia = categoryRepository.findByNameIgnoreCase("Moradia")
                        .orElseThrow(() -> new RuntimeException("Categoria 'Moradia' não encontrada"));
                Category categoryContas = categoryRepository.findByNameIgnoreCase("Roupas")
                        .orElseThrow(() -> new RuntimeException("Categoria 'Roupas' não encontrada"));

                Expense expense1 = new Expense();
                expense1.setDescription("Aluguel");
                expense1.setAmount(BigDecimal.valueOf(1200));
                expense1.setNumberOfInstallments(1);
                expense1.setCategory(categoryMoradia);
                expense1.setStartDate(LocalDate.of(2025, 8, 1));
                expense1.setPaymentMethod(PaymentMethod.DEBIT);
                expense1.setFixedExpense(true);
                expense1.setUser(user);
                expenseRepository.save(expense1);

                Expense expense2 = new Expense();
                expense2.setDescription("Blusas");
                expense2.setAmount(BigDecimal.valueOf(300));
                expense2.setNumberOfInstallments(2);
                expense2.setCategory(categoryContas);
                expense2.setStartDate(LocalDate.of(2025, 8, 5));
                expense2.setPaymentMethod(PaymentMethod.CREDIT);
                expense2.setFixedExpense(false);
                expense2.setUser(user);
                expenseRepository.save(expense2);
            }

            // Saldos de teste

            if (balanceRepository.count() == 0) {
                Balance balance1 = new Balance();
                balance1.setDescription("Salário");
                balance1.setAmount(BigDecimal.valueOf(3500));
                balance1.setFixed(true);
                balance1.setStartDate(LocalDate.of(2025, 1, 1));
                balance1.setUser(user);
                balanceRepository.save(balance1);

                Balance balance2 = new Balance();
                balance2.setDescription("Venda de notebook");
                balance2.setAmount(BigDecimal.valueOf(2000));
                balance2.setFixed(false);
                balance2.setStartDate(LocalDate.of(2025, 8, 10));
                balance2.setUser(user);
                balanceRepository.save(balance2);
            }
        };
    }
}
