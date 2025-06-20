package ait.cohort5860.dto;

import java.util.Map;

public record CurrencyDto(
        boolean success,
        long timestamp,
        String base,
        String date,
        Map<String, Double> rates
) {
}


