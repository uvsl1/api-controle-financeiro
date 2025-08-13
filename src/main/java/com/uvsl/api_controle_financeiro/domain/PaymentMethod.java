package com.uvsl.api_controle_financeiro.domain;

public enum PaymentMethod {
    DEBIT,
    CREDIT;

    public static PaymentMethod fromString(String method) {
        for (PaymentMethod pm : PaymentMethod.values()) {
            if (pm.name().equalsIgnoreCase(method)) {
                return pm;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inválido: " + method);
    }
}
