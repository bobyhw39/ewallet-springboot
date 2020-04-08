package com.enigma.seventhmarch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionGetDTO {
    private Long transactionId;
    private Date transactionDate;
    private String type;
    private String from;
    private String to;
    private BigDecimal amount;
    private String description;
}
