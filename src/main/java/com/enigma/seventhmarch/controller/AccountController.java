package com.enigma.seventhmarch.controller;

import com.enigma.seventhmarch.dto.*;
import com.enigma.seventhmarch.entity.Account;

//import com.enigma.seventhmarch.exceptions.AccountExceptions;
import com.enigma.seventhmarch.exceptions.ErrorDetails;
import com.enigma.seventhmarch.repository.AccountRepository;
import com.enigma.seventhmarch.services.AccountServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "E Wallet Applications")
@RequestMapping("account")
@Validated
public class AccountController {

    @Autowired
    AccountServices accountServices;

    @Autowired
    AccountRepository accountRepository;


    @GetMapping("/{accNum}")
    @ApiOperation(value = "View a info account", response = AccountGetDTO.class)
    public AccountGetDTO getAccount(@Valid @PathVariable String accNum) {
        return accountServices.getAccount(accNum);
    }

    @GetMapping("/search")
    @ApiOperation(value = "Search your account", response = AccountGetDTO.class)
    public List<AccountGetDTO> getAccountByName(@RequestParam String name) {
        return accountServices.getAccountByName(name);
    }

    @PostMapping("")
    @ApiOperation(value = "Create a account", response = AccountPostDTO.class)
    public Account saveAccount(@Valid @RequestBody AccountPostDTO accountPostDTO) {
        return accountServices.postAccount(accountPostDTO);
    }

    @PutMapping("/balance")
    @ApiOperation(value = "Update a Balance", response = AccountPutBalanceDTO.class)
    public AccountGetDTO updateBalance(@RequestBody AccountPutBalanceDTO updateBalance) {
        return accountServices.updateBalance(updateBalance);
    }

    @PutMapping("/status")
    @ApiOperation(value = "Update Status Account", response = AccountPutStatus.class)
    public AccountGetDTO updateStatus(@RequestBody AccountPutStatus updateStatus) {
        return accountServices.updateStatus(updateStatus);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login Account", response = AccountPostLogin.class)
    public ErrorDetails loginAccount(@RequestBody AccountPostLogin accountPostLogin){
        return accountServices.loginAccount(accountPostLogin);
    }

}
