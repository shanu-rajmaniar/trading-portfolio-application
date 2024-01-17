package com.example.portfolioapplication.service;

public interface TradeService {
    String[] performTransaction(String UserAccountId, String TradeType, Integer Quantity, String StockId);
}
