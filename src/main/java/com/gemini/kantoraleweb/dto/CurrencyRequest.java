package com.gemini.kantoraleweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CurrencyRequest (
        @NotBlank(message = "Kod waluty nie może być pusty")
        String currency,
        @Positive(message = "Kurs waluty musi być większy od zera")
        BigDecimal rate){
}
