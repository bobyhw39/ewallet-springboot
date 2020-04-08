package com.enigma.seventhmarch.services;

import com.enigma.seventhmarch.controller.AccountController;
import com.enigma.seventhmarch.controller.TransactionController;
import com.enigma.seventhmarch.dto.*;
import com.enigma.seventhmarch.entity.Account;
import com.enigma.seventhmarch.entity.Transaction;
import com.enigma.seventhmarch.exceptions.BadRequestException;
import com.enigma.seventhmarch.exceptions.NotFoundException;
import com.enigma.seventhmarch.repository.AccountRepository;
import com.enigma.seventhmarch.repository.TransactionRepository;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServices {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountServices accountServices;

    final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    Timestamp ts = new Timestamp(System.currentTimeMillis());
    Date dateNow = new Date(ts.getTime());


    private void checkPoint(AccountGetDTO account) {
        if (account.getPoint() == new BigDecimal(0.0)) {
            throw new NotFoundException("Account : " + account.getAccount() + " 0 Point");
        }
    }

    public Double getPoint(BigDecimal amount) {
        Double point = 0.0;
        BigDecimal minimum = new BigDecimal(10000);
        BigDecimal percent = new BigDecimal(10 / 100);
        if (amount.compareTo(minimum) > 0) {
            point = amount.multiply(percent).doubleValue();
        }
        if (point > 10000.00) {
            point = 10000.00;
        }
        return point;
    }

    private void checkBalance(AccountGetDTO account, BigDecimal ammount) {
        if (account.getBalance().compareTo(ammount) < 0) {
            throw new BadRequestException("Account : " + account.getAccount() + " not have sufficient");
        }
    }

    public List<TransactionGetDTO> getTransactionByAccountAndDate(String account, Date transactionDate) {
        List<TransactionGetDTO> transactionGetDTOS = Lists.newArrayList();
        List<Transaction> transactions = transactionRepository.findByFromAndTransactionDate(account, transactionDate);
        for (Transaction transaction : transactions) {
            ModelMapper modelMapper = new ModelMapper();
            TransactionGetDTO transactionGetDTO = modelMapper.map(transaction, TransactionGetDTO.class);
            transactionGetDTOS.add(transactionGetDTO);
        }
        logger.info("get transaction by account and date" + account + " " + transactionDate);
        return transactionGetDTOS;
    }

    public AccountGetDTO postBalance(TransactionPostBalanceDTO postBalanceDTO) {
        AccountGetDTO account = accountServices.getAccount(postBalanceDTO.getAccount());
        if (account == null) {
            throw new NotFoundException("Account Number : " + postBalanceDTO.getAccount() + " Not Found");
        }
        Account account1 = new Account(account.getId(), postBalanceDTO.getAccount(), account.getName(), (account.getBalance().add(postBalanceDTO.getBalance())), account.getPoint(), account.getStatus());
        Transaction transaction = new Transaction(null, dateNow, "balance", account.getAccount(), null, postBalanceDTO.getBalance(), "add balance");
        accountRepository.save(account1);
        transactionRepository.save(transaction);
        ModelMapper modelMapper = new ModelMapper();
        AccountGetDTO accountGetDTO = modelMapper.map(account, AccountGetDTO.class);
        logger.info("post balance");
        return accountGetDTO;
    }

    public String postTransactionPulsa(@Valid TransactionPostPulsaDTO transactionPostPulsaDTO) {
        AccountGetDTO account = accountServices.getAccount(transactionPostPulsaDTO.getAccount());
        checkBalance(account, transactionPostPulsaDTO.getAmount());
        Double point = getPoint(transactionPostPulsaDTO.getAmount());
        Transaction transaction = new Transaction(null, dateNow, "pulsa", account.getAccount(), null, transactionPostPulsaDTO.getAmount(), "Pembelian pulsa ke" + transactionPostPulsaDTO.getPhonenumber());
        Account accountCommit = new Account(account.getId(), transactionPostPulsaDTO.getAccount(), account.getName(), account.getBalance().subtract(transactionPostPulsaDTO.getAmount()), account.getPoint().add(new BigDecimal(point)), account.getStatus());
        transactionRepository.save(transaction);
        accountRepository.save(accountCommit);
        logger.info("post transaction pulsa from" + account + " to " + transactionPostPulsaDTO.getPhonenumber());
        return "pulsa to " + transactionPostPulsaDTO.getPhonenumber() + " success , you got " + point + " point";
    }

    public String postTransactionPoint(@Valid TransactionPostPointToBalanceDTO transactionPostPointToBalanceDTO) {
        AccountGetDTO account = accountServices.getAccount(transactionPostPointToBalanceDTO.getAccount());
        checkPoint(account);
        Transaction transaction = new Transaction(null, dateNow, "point", account.getAccount(), account.getAccount(), account.getPoint(), "point to balance");
        Account accountCommit = new Account(account.getId(), transactionPostPointToBalanceDTO.getAccount(), account.getName(), account.getBalance().add(account.getPoint()), account.getPoint().subtract(account.getPoint()), account.getStatus());
        transactionRepository.save(transaction);
        accountRepository.save(accountCommit);
        logger.info("post transaction point to balance");
        return "transfer point from " + transactionPostPointToBalanceDTO.getAccount() + " success";
    }

    public String postTransactionTransfer(@Valid TransactionPostTransferDTO transactionPostTransferDTO) {
        AccountGetDTO account = accountServices.getAccount(transactionPostTransferDTO.getAccount());
        checkBalance(account, transactionPostTransferDTO.getAmount());
        Account accountTo = accountRepository.findByAccount(transactionPostTransferDTO.getTo());
        Transaction transaction = new Transaction(null, dateNow, "transfer", transactionPostTransferDTO.getAccount(), transactionPostTransferDTO.getTo(), transactionPostTransferDTO.getAmount(), transactionPostTransferDTO.getDescription());
        Account accountCommit = new Account(account.getId(), transactionPostTransferDTO.getAccount(), account.getName(), account.getBalance().subtract(transactionPostTransferDTO.getAmount()), account.getPoint(), account.getStatus());
        Account accountToCommit = new Account(accountTo.getId(), transactionPostTransferDTO.getTo(), accountTo.getName(), accountTo.getBalance().add(transactionPostTransferDTO.getAmount()), accountTo.getPoint(), accountTo.getStatus());
        transactionRepository.save(transaction);
        accountRepository.save(accountCommit);
        accountRepository.save(accountToCommit);
        logger.info("transfer runned");
        return "success";
    }
}
