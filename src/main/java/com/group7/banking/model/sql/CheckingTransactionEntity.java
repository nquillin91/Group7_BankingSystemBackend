package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
public class CheckingTransactionEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    private String type;
    private double amount;
    private BigDecimal currentbalance;

    public CheckingTransactionEntity(String type, double amount, BigDecimal currentbalance) {
        this.type = type;
        this.amount = amount;
        this.currentbalance = currentbalance;
    }
}
