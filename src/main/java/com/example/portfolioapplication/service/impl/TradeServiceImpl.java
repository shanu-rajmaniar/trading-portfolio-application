package com.example.portfolioapplication.service.impl;

import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.entity.UserEntity;
import com.example.portfolioapplication.repository.HoldingRepository;
import com.example.portfolioapplication.repository.StockRepository;
import com.example.portfolioapplication.repository.UserRepository;
import com.example.portfolioapplication.service.TradeService;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Setter
@Getter
@NoArgsConstructor
@Service
@Transactional
public class TradeServiceImpl implements TradeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    @Autowired
    private StockRepository stockRepository;

    public String[] performTransaction(Integer userId, String tradeType, Integer quantity, String stockId) {
        HoldingEntity holding = holdingRepository.findByUserIdAndStockId(userId, stockId);
        UserEntity user = userRepository.findByUserId(userId);
        if(Objects.isNull(user)) {
            user = new UserEntity();
            user.setUserId(userId);
            userRepository.save(user);
        }
        HoldingEntity newHolding = new HoldingEntity();
        newHolding.setUserId(userId);
        newHolding.setStockId(stockId);
        if(Objects.isNull(holding)) {
            newHolding.setBuyPrice(0.0);
            newHolding.setQuantity(0);
        } else {
            newHolding.setBuyPrice(holding.getBuyPrice());
            newHolding.setQuantity(holding.getQuantity());
        }
        Integer currentStockQuantity = newHolding.getQuantity();
        Double stockBuyPrice = newHolding.getBuyPrice();
        StockEntity stock = stockRepository.findByStockId(stockId);
        String[] result = {"Success", "Order executed successfully!"};
        if(tradeType.equals("buy")) {
            buyStocks(newHolding, currentStockQuantity, stockBuyPrice, stock, quantity);
        } else if(currentStockQuantity >= quantity) {
            sellStocks(newHolding, currentStockQuantity, stockBuyPrice, stock, quantity);
        } else {
            result = new String[]{"Failure", "Not enough shares to sell!"};
        }
        if(!Objects.isNull(holding)) {
            holdingRepository.delete(holding);
        }
        return result;
    }

    public void buyStocks(HoldingEntity newHolding, Integer currentStockQuantity, Double stockBuyPrice, StockEntity stock, Integer quantity) {
        stockBuyPrice += quantity * stock.getClosePrice();
        currentStockQuantity += quantity;
        newHolding.setBuyPrice(stockBuyPrice);
        newHolding.setQuantity(currentStockQuantity);
        holdingRepository.save(newHolding);
    }

    public void sellStocks(HoldingEntity newHolding, Integer currentStockQuantity, Double stockBuyPrice, StockEntity stock, Integer quantity) {
        stockBuyPrice -= quantity * stock.getClosePrice();
        currentStockQuantity -= quantity;
        newHolding.setBuyPrice(stockBuyPrice);
        newHolding.setQuantity(currentStockQuantity);
        holdingRepository.save(newHolding);
    }
}
