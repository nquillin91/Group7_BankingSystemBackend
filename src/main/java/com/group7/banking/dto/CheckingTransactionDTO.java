package com.group7.banking.dto;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class CheckingTransactionDTO {
    private Long id;
    private Date date;
    private String type;
    private double amount;
    private BigDecimal currentbalance;

}
