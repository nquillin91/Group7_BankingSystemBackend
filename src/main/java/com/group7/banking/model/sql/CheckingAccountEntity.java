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

import antlr.collections.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
public class CheckingAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Getter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "CheckingAccount")
    private List<CheckingTransactionEntity> checkingTransactionsList;

    public List<CheckingTransaction> getCheckingTransactionList() {
        return checkingTransactionList;
    }

    public void setCheckingTransaction(List<CheckingTransaction> checkingTransactionList) {
        this.checkingTransactionList = checkingTransactionList;

    }

}