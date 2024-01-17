package com.example.portfolioapplication.service.impl;

import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.repository.HoldingRepository;
import com.example.portfolioapplication.repository.StockRepository;
import com.example.portfolioapplication.repository.UserRepository;
import com.example.portfolioapplication.service.TradeService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Setter
@Getter
@NoArgsConstructor
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private StockRepository stockRepository;

    public String[] performTransaction(String userId, String tradeType, Integer quantity, String stockId) {
        HoldingEntity holding = holdingRepository.findByUserIdAndStockId(userId, stockId);
        if(holding.equals(null)) {
            holding.setUserId(userId);
            holding.setStockId(stockId);
            holding.setBuyPrice(0.0);
            holding.setQuantity(0.0);
        }
        Double currentStockQuantity = holding.getQuantity();
        Double stockBuyPrice = holding.getBuyPrice();
        StockEntity stock = stockRepository.findByStockId(stockId);
        String[] result = {"Success", "Order executed successfully!"};
        if(tradeType.equals("buy")) {
            stockBuyPrice += quantity * stock.getClosePrice();
            currentStockQuantity += quantity;
            holding.setBuyPrice(stockBuyPrice);
            holding.setQuantity(currentStockQuantity);
            holdingRepository.save(holding);
        } else if(currentStockQuantity >= quantity) {
            stockBuyPrice -= quantity * stock.getClosePrice();
            currentStockQuantity -= quantity;
            holding.setBuyPrice(stockBuyPrice);
            holding.setQuantity(currentStockQuantity);
            holdingRepository.save(holding);
        } else {
            result = new String[]{"Failure", "Not enough shares to sell!"};
        }
        return  result;
    }
}
