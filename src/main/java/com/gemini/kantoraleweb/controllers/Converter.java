package com.gemini.kantoraleweb.controllers;

import com.gemini.kantoraleweb.dto.CurrencyRequest;
import com.gemini.kantoraleweb.exception.CurrencyNotFoundException;
import com.gemini.kantoraleweb.service.Kantor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/convert")
public class Converter {
    private final Kantor kantor;

    public Converter(Kantor kantor){
        this.kantor = kantor;
    }


    //@PostMapping("/addCurrency/{currency}/{rate}")
    //public ResponseEntity<String> addCurrency(@PathVariable String currency, @PathVariable BigDecimal rate){
    //    kantor.addCurrency(currency, rate);
    //    return ResponseEntity.status(HttpStatus.CREATED).body("Dodano walutę: " + currency + " o rate: " + rate);
    //

    @PostMapping("/addCurrency")
    public ResponseEntity<String> addCurrency(@Validated @RequestBody List<@Valid CurrencyRequest> requests){
        for(CurrencyRequest request : requests) {
            kantor.addCurrency(request.currency(), request.rate());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Pomyślnie dodano/zaktualizowano " + requests.size() + " walut.");
    }

    @GetMapping("/getRate/{fromToCurrency}")
    public ResponseEntity<BigDecimal> getRateExchange(@PathVariable String fromToCurrency){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getExchangeRate(fromToCurrency));
    }

    @GetMapping("/getRate")
    public ResponseEntity<Map<String, BigDecimal>> getAllExchangeRates(){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getAllExchangeRates());
    }

    @DeleteMapping("/deleteRate/{currency}")
    public ResponseEntity<String> deleteCurrency(@PathVariable String currency) throws CurrencyNotFoundException {
        kantor.deleteCurrency(currency);
        return ResponseEntity.ok("Usunieto " + currency);
    }

    @GetMapping("/exchangeCurrency/{currency}")
    public ResponseEntity<BigDecimal> exchangeCurrency(@PathVariable String currency, @RequestParam BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.exchangeCurrency(currency, amount));
    }
}
