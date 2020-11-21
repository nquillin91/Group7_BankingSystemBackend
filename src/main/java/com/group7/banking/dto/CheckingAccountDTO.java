package com.group7.banking.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.group7.banking.model.sql.CheckingTransactionEntity;
import com.group7.banking.model.sql.UserEntity;

import lombok.Data;

@Data
public class CheckingAccountDTO {

    public static enum Type {
        WITHDRAW, DEPOSIT
    }

    private long id;
    UserEntity user;
    private Type accountType;
    private Double balance;
    private List<CheckingTransactionEntity> depositTransactions;
    private List<CheckingTransactionEntity> withdrawTransactions;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

}
