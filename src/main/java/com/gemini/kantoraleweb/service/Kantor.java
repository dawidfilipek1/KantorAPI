package com.gemini.kantoraleweb.service;

import com.gemini.kantoraleweb.exception.CurrencyNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Kantor {
    private final Map<String, BigDecimal> currencyExchange = new ConcurrentHashMap<>();

    public void addCurrency(String currency, BigDecimal rate){
        currencyExchange.put(currency, rate);
    }

    public BigDecimal exchangeCurrency(String fromToCurrency, BigDecimal amount){
        if(currencyExchange.containsKey(fromToCurrency)){
            return amount.multiply(currencyExchange.get(fromToCurrency)).setScale(2, RoundingMode.HALF_UP);
        }
        throw new CurrencyNotFoundException("Currency not found");
    }

    public BigDecimal getExchangeRate(String currency){
        if(currencyExchange.containsKey(currency)){
            return currencyExchange.get(currency);
        }
        throw new CurrencyNotFoundException("Currency not found");
    }

    public List<BigDecimal> getExchangeRate(List<String> currencies){
        List<BigDecimal> list = new ArrayList<>();
        for(String currency : currencies){
            list.add(getExchangeRate(currency));
        }
        return list;
    }

    public void deleteCurrency(String currency){
        if(!currencyExchange.containsKey(currency)) {
            throw new CurrencyNotFoundException("Currency not found");
        }
        currencyExchange.remove(currency);
    }

    public Map<String, BigDecimal> getAllExchangeRates(){
        return Collections.unmodifiableMap(currencyExchange);
    }
}
