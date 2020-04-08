package com.enigma.seventhmarch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountGetDTO {
    private Long id;
    private String account;
    private String name;
    private BigDecimal balance;
    private BigDecimal point;
    private Integer status;
}
