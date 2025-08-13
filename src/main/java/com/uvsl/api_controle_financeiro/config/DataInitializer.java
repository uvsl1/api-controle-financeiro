package com.uvsl.api_controle_financeiro.config;

import com.uvsl.api_controle_financeiro.domain.Category;
import com.uvsl.api_controle_financeiro.repositories.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository) {
        return args -> {
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
        };
    }
}
