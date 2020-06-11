package com.enigma.seventhmarch.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Setter
@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_tbl")
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "account", length = 6, nullable = false)
    private String account;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "point", nullable = false)
    private BigDecimal point;

    @Column(name = "status", nullable = false)
    private Integer status;


}
