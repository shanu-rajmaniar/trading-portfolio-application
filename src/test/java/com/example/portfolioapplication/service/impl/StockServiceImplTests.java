package com.example.portfolioapplication.service.impl;

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
public class StockServiceImplTests {

    @InjectMocks
    private StockServiceImpl stockService;

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
    public void fetchStockDetailsTests() {
        StockEntity newStock = new StockEntity("INE669E01016", "ABCD", 100.0, 200.0, 300.0, 400.0, 500.0);
        Mockito.when(stockRepository.findByStockId("INE669E01016")).thenReturn(new StockEntity("INE669E01016", "ABCD", 100.0, 200.0, 300.0, 400.0, 500.0));
        StockEntity stock = stockService.fetchStockDetails("INE669E01016");

        verify(stockRepository).findByStockId(argThat(argument -> {
            return argument.equals("INE669E01016");
        }));

        Assertions.assertEquals(newStock.getStockId(), stock.getStockId());
        Assertions.assertEquals(newStock.getStockName(), stock.getStockName());
    }

    @Test
    public void fetchUserHoldings() {
        stockService.fetchUserHoldings(1);

        verify(holdingRepository).findByUserId(argThat(argument -> {
            return argument.equals(1);
        }));
    }
}
