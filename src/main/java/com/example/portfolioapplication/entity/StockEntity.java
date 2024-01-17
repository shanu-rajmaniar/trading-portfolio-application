package com.example.portfolioapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "stocks")
public class StockEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    String stockId;

    String stockName;

    Double openPrice;

    Double highPrice;

    Double lowPrice;

    Double closePrice;

    Double settlementPrice;
}