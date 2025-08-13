package com.uvsl.api_controle_financeiro.services;

import com.uvsl.api_controle_financeiro.domain.Category;
import com.uvsl.api_controle_financeiro.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import com.uvsl.api_controle_financeiro.dtos.CategoryDTO;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getId(), category.getName()))
                .toList();
    }
}
