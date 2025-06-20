package ait.cohort5860.controller;

import ait.cohort5860.dto.CurrencyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class CurrencyExchangeController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${fixer.api.url}")
    private String fixerApiUrl;

    @Value("${fixer.api.key}")
    private String fixerApiKey;

    @PostMapping("/rate")
    public Double getRate(@RequestParam String fromCurrency, @RequestParam String toCurrency) {

        String url =fixerApiUrl
                + "latest?access_key=" + fixerApiKey
                + "&base=" + fromCurrency.toUpperCase()
                + "&symbols=" + toCurrency.toUpperCase();

//        System.out.println("Final URL: " + url);

        CurrencyDto response;
        try {
            response = restTemplate.getForObject(url, CurrencyDto.class);
        } catch (Exception e) {
            throw new RuntimeException("HTTP error while accessing Fixer.io API.", e);
        }

        Double rate = response.getRates().get(toCurrency.toUpperCase());
        if (rate == null) {
            throw new RuntimeException("Exchange rate for " + toCurrency + " not found.");
        }

        return rate;
    }
}
