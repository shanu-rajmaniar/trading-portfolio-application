package com.example.portfolioapplication.service;

public interface TradeService {
    String[] performTransaction(Integer UserId, String TradeType, Integer Quantity, String StockId);
}
