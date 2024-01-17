package com.example.portfolioapplication.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StockDetailResponseDTO {
    String stockId;
    String stockName;
    Double openPrice;
    Double highPrice;
    Double lowPrice;
    Double closePrice;
    Double settlementPrice;
}
