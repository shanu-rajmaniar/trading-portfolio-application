package com.example.portfolioapplication.service.impl;

import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.repository.HoldingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class TradeServiceImplTests {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private HoldingRepository holdingRepository;

    @Test
    public void canary() {
        Assertions.assertTrue(true);
    }
    @BeforeEach
    public void setUp() {
        // Initialize annotated mocks
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buyStocksTests() {
        HoldingEntity newHolding = new HoldingEntity();
        Integer currentStockQuantity = 10;
        Double stockBuyPrice = 100.0;
        StockEntity stock = new StockEntity();
        stock.setClosePrice(20.0);
        Integer quantity = 5;

        tradeService.buyStocks(newHolding, currentStockQuantity, stockBuyPrice, stock, quantity);

        verify(holdingRepository).save(argThat(argument -> {
            return argument.getBuyPrice().equals(200.0) && argument.getQuantity().equals(15);
        }));
    }

    @Test
    public void sellStocksTests() {
        HoldingEntity newHolding = new HoldingEntity();
        Integer currentStockQuantity = 10;
        Double stockBuyPrice = 200.0;
        StockEntity stock = new StockEntity();
        stock.setClosePrice(20.0);
        Integer quantity = 5;

        tradeService.sellStocks(newHolding, currentStockQuantity, stockBuyPrice, stock, quantity);

        verify(holdingRepository).save(argThat(argument -> {
            return argument.getBuyPrice().equals(100.0) && argument.getQuantity().equals(5);
        }));
    }
}
