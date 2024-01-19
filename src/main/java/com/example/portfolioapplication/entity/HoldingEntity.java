package com.example.portfolioapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "holdings")
public class HoldingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String userId;

    private Double buyPrice;

    private String stockId;

    private Integer quantity;

}
