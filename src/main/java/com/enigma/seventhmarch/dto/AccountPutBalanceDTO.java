package com.enigma.seventhmarch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountPutBalanceDTO {

    @NotEmpty(message = "Account may not be empty")
    @Size(min = 6, max = 6, message = "Account must be 6 length")
    private String account;

    @NotNull(message = "Balance may not be empty")
    @Min(value = (long) 10000.0, message = "insufience Balance")
    private BigDecimal balance;

}
