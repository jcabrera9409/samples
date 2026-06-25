package com.webflux.dto;

import java.util.HashMap;

public record APIExternaResponseDTO (
    Double amount,
    String base,
    String date,
    HashMap<String, Double> rates
) { }
