package com.metamafitness.fitnessbackend.service;

import java.math.BigDecimal;


public interface CurrencyConversionService {
    BigDecimal convert(String fromCurrency, String toCurrency, BigDecimal amount);

}
