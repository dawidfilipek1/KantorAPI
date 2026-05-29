package com.gemini.kantoraleweb.service;

import com.gemini.kantoraleweb.exception.CurrencyNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Kantor {
    private final Map<String, Double> currencyExchange = new ConcurrentHashMap<>();

    public void addCurrency(String currency, double rate){
        currencyExchange.put(currency, rate);
    }

    public double exchangeCurrency(String fromToCurrency, double amount) throws CurrencyNotFoundException {
        if(currencyExchange.containsKey(fromToCurrency)){
            return Math.round(currencyExchange.get(fromToCurrency) * amount * 100) / 100.0;
        }
        throw new CurrencyNotFoundException("Currency not found");
    }

    public double getExchangeRate(String currency) throws CurrencyNotFoundException {
        if(currencyExchange.containsKey(currency)){
            return currencyExchange.get(currency);
        }
        throw new CurrencyNotFoundException("Currency not found");
    }

    public void deleteCurrency(String currency) throws CurrencyNotFoundException {
        if(!currencyExchange.containsKey(currency)) {
            throw new CurrencyNotFoundException("Currency not found");
        }
        currencyExchange.remove(currency);
    }

    public List<String> getAllExchangeRates(){
        List<String> results = new ArrayList<>();

        for(String key : currencyExchange.keySet()){
            results.add(key + ": " + currencyExchange.get(key));
        }
        return results;
    }
}
