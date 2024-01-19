package com.example.portfolioapplication.controller;

import com.example.portfolioapplication.dto.StockDetailResponseDTO;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.service.impl.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintStream;

@RestController
@Controller
@RequestMapping("/api/v1/")
public class StockController {

    @Autowired
    private StockServiceImpl stockService;

    @GetMapping(value = "/stock/{stock_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    StockDetailResponseDTO getStockDetails(
            @PathVariable("stock_id") String stockId) {
        System.out.println("Stock Id -> " + stockId);
        StockEntity stock = stockService.fetchStockDetails(stockId);
        System.out.println("Stock Name -> " + stock.getStockName());
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
