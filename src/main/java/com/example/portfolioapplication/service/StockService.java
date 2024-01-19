package com.example.portfolioapplication.service;

import com.example.portfolioapplication.dto.PortfolioResponseDTO;
import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public interface StockService {
    StockEntity fetchStockDetails(String stockId);

    List<HoldingEntity> fetchUserHoldings(Integer userId);

    PortfolioResponseDTO calculateUserPortfolio(List<HoldingEntity> holdings);

    void downloadAndExtractNseCmBhavcopy() throws IOException;

    void downloadFile(String url, String destinationPath) throws IOException;

    void extractAndSaveAsCsv(String zipFilePath, String destinationPath) throws IOException;

    void readStockDetailsFromCsv(String filePath) throws IOException, ParseException;

    void saveStockDetailsToDb(BufferedReader inputReader) throws IOException, ParseException;
}
