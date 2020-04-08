package com.enigma.seventhmarch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountPostDTO {

    @NotEmpty(message = "Account may not be empty")
    @Size(min = 6, max = 6, message = "Account must be 6 length")
    private String account;

    @Valid
    @NotNull(message = "Name can not be null")
    @Size(min = 5, message = "Name minimum 5 character")
    private String name;

    @NotNull(message = "Balance may not be empty")
    @Min(value = (long) 1000000.0, message = "insufience Balance")
    private BigDecimal balance;
}
