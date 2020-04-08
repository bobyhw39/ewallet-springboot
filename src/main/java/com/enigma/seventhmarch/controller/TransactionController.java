package com.enigma.seventhmarch.controller;

import com.enigma.seventhmarch.dto.*;
import com.enigma.seventhmarch.exceptions.ErrorDetails;
import com.enigma.seventhmarch.repository.TransactionRepository;
import com.enigma.seventhmarch.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {

    @Autowired
    TransactionServices transactionServices;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/search")
    public List<TransactionGetDTO> getTransactionByAccountAndDate(@RequestParam String account, @RequestParam Date transactionDate) {
        return transactionServices.getTransactionByAccountAndDate(account, transactionDate);
    }

    @PostMapping("/balance")
    public ErrorDetails balance(@RequestBody TransactionPostBalanceDTO transactionPostBalanceDTO) {
        transactionServices.postBalance(transactionPostBalanceDTO);
        return new ErrorDetails(new Date(System.currentTimeMillis()), "Add balance success", "200", "/transaction/balance");
    }

    @PostMapping("/pulsa")
    public ErrorDetails pulsa(@RequestBody TransactionPostPulsaDTO transactionPostPulsaDTO) {
        transactionServices.postTransactionPulsa(transactionPostPulsaDTO);
        return new ErrorDetails(new Date(System.currentTimeMillis()), "pulsa to " + transactionPostPulsaDTO.getPhonenumber() + " success", "200", "/transaction/pulsa");
    }

    @PostMapping("/point")
    public ErrorDetails point(@RequestBody TransactionPostPointToBalanceDTO transactionPostPointToBalanceDTO) {
        transactionServices.postTransactionPoint(transactionPostPointToBalanceDTO);
        return new ErrorDetails(new Date(System.currentTimeMillis()), "tranfer point from " + transactionPostPointToBalanceDTO.getAccount() + " success", "200", "/transaction/point");
    }

    @PostMapping("/transfer")
    public ErrorDetails transfer(@RequestBody TransactionPostTransferDTO transactionPostTransferDTO) {
        transactionServices.postTransactionTransfer(transactionPostTransferDTO);
        return new ErrorDetails(new Date(System.currentTimeMillis()), "tranfer from " + transactionPostTransferDTO.getAccount() + " to " + transactionPostTransferDTO.getTo() + " Success ", "200", "/transaction/transfer");
    }
}

