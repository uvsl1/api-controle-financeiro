package com.uvsl.api_controle_financeiro.dtos;

import java.math.BigDecimal;
import java.util.List;

public record AllCategoryExpenseSummaryDTO(Long userId, String month, BigDecimal total, List<CategoryExpenseSummaryDTO> categories
) {}
