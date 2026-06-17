package com.gemini.kantoraleweb.controllers;

import com.gemini.kantoraleweb.dto.CurrencyRequest;
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
    public ResponseEntity<String> addCurrency(@RequestBody @Valid CurrencyRequest request){
        kantor.addCurrency(request.currency(), request.rate());
        return ResponseEntity.status(HttpStatus.CREATED).body("Dodano walutę: " + request.currency() + " o rate: " + request.rate());
    }

    @PostMapping("/addCurrencies")
    public ResponseEntity<String> addCurrency(@Validated @RequestBody List<@Valid CurrencyRequest> requests){
        for(CurrencyRequest request : requests) {
            kantor.addCurrency(request.currency(), request.rate());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Pomyślnie dodano/zaktualizowano " + requests.size() + " walut.");
    }

    //@GetMapping("/getRate/{fromToCurrency}")
    //public ResponseEntity<BigDecimal> getRateExchange(@PathVariable String fromToCurrency){
    //    return ResponseEntity.status(HttpStatus.OK).body(kantor.getExchangeRate(fromToCurrency));
    //}

    @GetMapping("/getRate")
    public ResponseEntity<BigDecimal> getRateExchange(@RequestBody @Valid String fromToCurrency){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getExchangeRate(fromToCurrency));
    }

    @GetMapping("/getRates")
    public ResponseEntity<List<BigDecimal>> getRateExchange(@Validated @RequestBody List<@Valid String> fromToCurrencies){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getExchangeRate(fromToCurrencies));
    }

    @GetMapping("/getAllRates")
    public ResponseEntity<Map<String, BigDecimal>> getAllExchangeRates(){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.getAllExchangeRates());
    }

    @DeleteMapping("/deleteRate")
    public ResponseEntity<String> deleteCurrency(@RequestBody @Valid String currency){
        kantor.deleteCurrency(currency);
        return ResponseEntity.ok("Usunieto " + currency);
    }

    @GetMapping("/exchangeCurrency/{currency}")
    public ResponseEntity<BigDecimal> exchangeCurrency(@PathVariable String currency, @RequestParam BigDecimal amount){
        return ResponseEntity.status(HttpStatus.OK).body(kantor.exchangeCurrency(currency, amount));
    }
}
