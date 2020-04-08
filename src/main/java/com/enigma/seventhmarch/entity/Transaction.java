package com.enigma.seventhmarch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions_tbl")
public class Transaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "transactions_id", nullable = false)
    private Long transactionId;

    @Column(name = "transactions_date", nullable = false)
    private Date transactionDate;

    @Column(name = "types", nullable = false)
    private String type;

    @Column(name = "froms", nullable = false)
    private String from;

    @Column(name = "tos", nullable = true)
    private String to;

    @Column(name = "amounts", nullable = false)
    private BigDecimal amount;

    @Column(name = "descriptions", nullable = false)
    private String description;


}
