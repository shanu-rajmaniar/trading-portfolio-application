package com.example.portfolioapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class PortfolioResponseDTO {
    public List<Holdings> holdings;
    public Double totalPortfolioHolding;
    public Double totalBuyPrice;
    public Double totalProfitAndLoss;
    public Double totalPnlPercent;
}
