package com.gemini.kantoraleweb.controllers;

import com.gemini.kantoraleweb.exception.CurrencyNotFoundException;
import com.gemini.kantoraleweb.service.Kantor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convert")
public class Converter {
    private final Kantor kantor;

    public Converter(Kantor kantor){
        this.kantor = kantor;
    }


    @PostMapping("/addCurrency/{currency}/{rate}")
    public ResponseEntity<String> addCurrency(@PathVariable String currency, @PathVariable double rate){
        kantor.addCurrency(currency, rate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano walutę: " + currency + " o rate: " + rate);

    }

    @GetMapping("/getRate/{fromToCurrency}")
    public ResponseEntity<Double> getRateExchange(@PathVariable String fromToCurrency) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getExchangeRate(fromToCurrency));
    }

    @GetMapping("/getRate")
    public ResponseEntity<List<String>> getAllExchangeRates(){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getAllExchangeRates());
    }

    @DeleteMapping("/deleteRate/{currency}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String currency) throws CurrencyNotFoundException {
        kantor.deleteCurrency(currency);
        return ResponseEntity.ok("Usunieto " + currency);
    }

    @GetMapping("/exchangeCurrency/{currency}/{amount}")
    public ResponseEntity<Double> exchangeCurrency(@PathVariable String currency, @PathVariable double amount) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(kantor.exchangeCurrency(currency, amount));
    }
}
