package com.example.portfolioapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class  UserEntity {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String userId;

    private Integer accountBalance;
}
