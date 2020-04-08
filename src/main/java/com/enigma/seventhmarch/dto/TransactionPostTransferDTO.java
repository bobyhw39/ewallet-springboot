package com.enigma.seventhmarch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionPostTransferDTO {

    @NotEmpty(message = "Account may not be empty")
    @Size(min = 6, max = 6, message = "Account must be 6 length")
    private String account;

    @NotEmpty(message = "Account to may not be empty")
    @Size(min = 6, max = 6, message = "Account must be 6 length")
    private String to;

    @NotNull(message = "Ammount may not be empty")
    private BigDecimal amount;

    @NotEmpty(message = "Description may not be empty")
    private String description;
}
