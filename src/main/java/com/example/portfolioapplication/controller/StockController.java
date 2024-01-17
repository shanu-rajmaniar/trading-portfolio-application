package com.example.portfolioapplication.controller;

import com.example.portfolioapplication.dto.StockDetailResponseDTO;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.service.impl.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/")
public class StockController {

    @Autowired
    private StockServiceImpl stockService;

    @GetMapping("/stock/{stock_id}")
    StockDetailResponseDTO getStockDetails(
            @PathVariable("stock_id") String stockId) {
        StockEntity stock = stockService.fetchStockDetails(stockId);
        StockDetailResponseDTO response = new StockDetailResponseDTO(stock.getStockId(), stock.getStockName(), stock.getOpenPrice(),
                stock.getHighPrice(), stock.getLowPrice(), stock.getClosePrice(), stock.getSettlementPrice());
        return response;
    }

    @PostMapping("/stock")
    String updateStockDetails() throws IOException {
        stockService.downloadAndExtractNseCmBhavcopy();
        return "Done";
    }
}
