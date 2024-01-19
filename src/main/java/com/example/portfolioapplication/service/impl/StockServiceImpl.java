package com.example.portfolioapplication.service.impl;

import com.example.portfolioapplication.dto.Holdings;
import com.example.portfolioapplication.dto.PortfolioResponseDTO;
import com.example.portfolioapplication.entity.HoldingEntity;
import com.example.portfolioapplication.entity.StockEntity;
import com.example.portfolioapplication.repository.HoldingRepository;
import com.example.portfolioapplication.repository.StockRepository;
import com.example.portfolioapplication.service.StockService;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private HoldingRepository holdingRepository;

    public StockEntity fetchStockDetails(String stockId) {
        StockEntity stock = stockRepository.findByStockId(stockId);
        return stock;
    }

    public List<HoldingEntity> fetchUserHoldings(String userId) {
        List<HoldingEntity> holdings = holdingRepository.findByUserId(userId);
        return holdings;
    }

    public PortfolioResponseDTO calculateUserPortfolio(@NotNull List<HoldingEntity> holdings) {
        PortfolioResponseDTO userPortfolio = new PortfolioResponseDTO();
        Double totalPortfolioHolding = 0.0;
        Double totalBuyPrice = 0.0;
        Double totalProfitAndLoss = 0.0;
        for(HoldingEntity holding : holdings) {
            Holdings stockHolding = new Holdings();
            stockHolding.setStockId(holding.getStockId());
            stockHolding.setStockName(fetchStockDetails(holding.getStockId()).getStockName());
            stockHolding.setQuantity(holding.getQuantity());
            stockHolding.setBuyPrice(holding.getBuyPrice());
            totalBuyPrice += holding.getBuyPrice();
            stockHolding.setCurrentPrice(fetchStockDetails(holding.getStockId()).getClosePrice());
            stockHolding.setPnl(stockHolding.getCurrentPrice() - stockHolding.getBuyPrice());
            userPortfolio.holdings.add(stockHolding);
            totalPortfolioHolding += stockHolding.getQuantity() * stockHolding.getCurrentPrice();
            totalProfitAndLoss += (stockHolding.getCurrentPrice() - stockHolding.getBuyPrice()) * stockHolding.getQuantity();
        }
        userPortfolio.setTotalBuyPrice(totalBuyPrice);
        userPortfolio.setTotalPortfolioHolding(totalPortfolioHolding);
        userPortfolio.setTotalBuyPrice(totalBuyPrice);
        userPortfolio.setTotalProfitAndLoss(totalProfitAndLoss);
        userPortfolio.setTotalPnlPercent(totalProfitAndLoss / totalBuyPrice);
        return userPortfolio;
    }

    public void  downloadAndExtractNseCmBhavcopy() throws IOException {
        String baseUrl = "https://archives.nseindia.com/content/historical/EQUITIES/";
        String destinationPath = "/Users/shanurm/Downloads/quickstart/";

        LocalDate currentDate = LocalDate.now().minusDays(1);
        String formattedDateFirst = currentDate.format(DateTimeFormatter.ofPattern("yyyy/MMM"));
        String formattedDateSecond = currentDate.format(DateTimeFormatter.ofPattern("ddMMyyyy"));

        baseUrl += formattedDateFirst.toUpperCase() + "/NSE_CM_bhavcopy_" + formattedDateSecond + ".csv.zip";

        try {
            downloadFile(baseUrl, destinationPath);
            extractAndSaveAsCsv(destinationPath + "NSE_CM_bhavcopy.csv.zip", destinationPath);
            readStockDetailsFromCsv(destinationPath + "NSE_CM_bhavcopy_" + formattedDateSecond + ".csv");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(String url, String destinationPath) throws IOException {
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(destinationPath, "NSE_CM_bhavcopy.csv.zip"), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void extractAndSaveAsCsv(String zipFilePath, String destinationPath) throws IOException {
        try (ZipArchiveInputStream zipInput = new ZipArchiveInputStream(new FileInputStream(zipFilePath))) {
            ZipArchiveEntry entry;
            while ((entry = zipInput.getNextZipEntry()) != null) {
                if (!entry.isDirectory()) {
                    String entryFileName = entry.getName();
                    if (entryFileName.endsWith(".csv")) {
                        String outputPath = destinationPath + entryFileName;
                        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(outputPath))) {
                            IOUtils.copy(zipInput, os);
                        }
                    }
                }
            }
        }
    }

    public void readStockDetailsFromCsv(String bhavCopyFile) throws IOException, ParseException {
        File inputFile = new File(bhavCopyFile);
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(bhavCopyFile)));
        saveStockDetailsToDb(inputReader);
    }

    public void saveStockDetailsToDb(BufferedReader inputReader) throws IOException, ParseException {
        String line = null;
        boolean firstLine = true;
        while((line = inputReader.readLine()) != null) {
            if(firstLine) {
                firstLine = false;
                continue;
            }
            String[] columns = line.split(",");
            String stockId = columns[0];
            String stockName = columns[1];
            Double openPrice = Double.valueOf(columns[5]);
            Double highPrice = Double.valueOf(columns[6]);
            Double lowPrice = Double.valueOf(columns[7]);
            Double closePrice = Double.valueOf(columns[8]);
            Double settlementPrice = Double.valueOf(columns[25]);
            StockEntity stock = new StockEntity(stockId, stockName, openPrice, highPrice, lowPrice, closePrice, settlementPrice);
            stockRepository.save(stock);
        }
    }
}
