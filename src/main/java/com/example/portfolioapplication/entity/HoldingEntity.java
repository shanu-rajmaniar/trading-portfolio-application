package com.example.portfolioapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "holdings")
public class HoldingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String Id;

    private String userId;

    private Double buyPrice;

    private String stockId;

    private Double quantity;

}
