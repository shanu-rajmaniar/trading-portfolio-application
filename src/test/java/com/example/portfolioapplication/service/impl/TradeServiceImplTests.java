package com.example.portfolioapplication.service.impl;

import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.repository.HoldingRepository;
import com.example.portfolioapplication.repository.StockRepository;
import com.example.portfolioapplication.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@Transactional
public class TradeServiceImplTests {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StockRepository stockRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canary() {
        Assertions.assertTrue(true);
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

    @Test
    public void performTransactionTestsNewUser() {
        Mockito.when(stockRepository.findByStockId("INE669E01016")).thenReturn(new StockEntity("INE669E01016", "INE669E01016", 1.0, 1.0, 1.0, 1.0, 1.0));
        String[] result = tradeService.performTransaction(1, "buy", 100, "INE669E01016");

        verify(userRepository).save(argThat(argument -> {
            return argument.getUserId().equals(1);
        }));

        verify(holdingRepository).save(argThat(argument -> {
            return argument.getUserId().equals(1) && argument.getStockId().equals("INE669E01016");
        }));

        Assertions.assertEquals(result[0], "Success");
        Assertions.assertEquals(result[1], "Order executed successfully!");
    }

    @Test
    public void performTransactionTests() {
        Mockito.when(stockRepository.findByStockId("INE669E01016")).thenReturn(new StockEntity("INE669E01016", "INE669E01016", 1.0, 1.0, 1.0, 1.0, 1.0));
        Mockito.when(holdingRepository.findByUserIdAndStockId(1, "INE669E01016")).thenReturn(new HoldingEntity(1, 1, 500.0, "INE669E01016", 20));
        String[] result = tradeService.performTransaction(1, "buy", 100, "INE669E01016");

        verify(userRepository).save(argThat(argument -> {
            return argument.getUserId().equals(1);
        }));

        verify(holdingRepository).save(argThat(argument -> {
            return argument.getUserId().equals(1) && argument.getStockId().equals("INE669E01016");
        }));

        Assertions.assertEquals(result[0], "Success");
        Assertions.assertEquals(result[1], "Order executed successfully!");
    }
}
