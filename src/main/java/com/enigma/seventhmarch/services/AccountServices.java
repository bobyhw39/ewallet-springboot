package com.enigma.seventhmarch.services;

import com.enigma.seventhmarch.controller.AccountController;
import com.enigma.seventhmarch.dto.AccountGetDTO;
import com.enigma.seventhmarch.dto.AccountPostDTO;
import com.enigma.seventhmarch.dto.AccountPutBalanceDTO;
import com.enigma.seventhmarch.dto.AccountPutStatus;
import com.enigma.seventhmarch.entity.Account;
import com.enigma.seventhmarch.exceptions.BadRequestException;
import com.enigma.seventhmarch.exceptions.NotFoundException;
import com.enigma.seventhmarch.repository.AccountRepository;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@GwtCompatible
@Service
public class AccountServices {

    @Autowired
    AccountRepository accountRepository;

    final Logger logger = LoggerFactory.getLogger(AccountController.class);

    private void checkStatus(Integer account) {
        if (account == 1) {
            throw new NotFoundException("account not active");
        } else if (account > 1) {
            throw new NotFoundException("Status not deviine");
        }
    }

    public AccountGetDTO getAccount(String accNum) {
        logger.info("get account of " + accNum);
        Account account = accountRepository.findByAccount(accNum);
        if (account == null) {
            throw new NotFoundException("Account Number : " + accNum + " Not Found");
        }
        ModelMapper modelMapper = new ModelMapper();
        AccountGetDTO accountGetDTO = modelMapper.map(account, AccountGetDTO.class);
        return accountGetDTO;
    }

    public List<AccountGetDTO> getAccountByName(String name) {
        logger.info("search account with name " + name);
        List<AccountGetDTO> accountGetDTOList = Lists.newArrayList();
        List<Account> accounts = accountRepository.findByName(name);
        if (accountGetDTOList.size() == 0) {
            throw new NotFoundException("name : " + name + " Not Found");
        }
        for (Account acc : accounts
        ) {
            ModelMapper modelMapper = new ModelMapper();
            AccountGetDTO accountGetDTO = modelMapper.map(acc, AccountGetDTO.class);
            accountGetDTOList.add(accountGetDTO);
        }
        if (accountGetDTOList.size() == 0) {
            throw new NotFoundException("name : " + name + " Not Found");
        }
        return accountGetDTOList;
    }

    public Account postAccount(AccountPostDTO postDTO) {
        logger.info("Post new account " + postDTO.getAccount());
        Account checkAccount = accountRepository.findByAccount(postDTO.getAccount());
        if (checkAccount != null) {
            throw new BadRequestException("Account Number : " + postDTO.getAccount() + " Found Cannot Create Account");
        }
        Account account = new Account(null, postDTO.getAccount(), postDTO.getName(), postDTO.getBalance(), new BigDecimal(10000), 0);
        accountRepository.save(account);
        return account;
    }

    public AccountGetDTO updateBalance(AccountPutBalanceDTO putBalanceDTO) {
        Account account = accountRepository.findByAccount(putBalanceDTO.getAccount());
        if (account == null) {
            throw new NotFoundException("Account Number : " + putBalanceDTO.getAccount() + " Not Found");
        }
        checkStatus(account.getStatus());
        Account account1 = new Account(account.getId(), putBalanceDTO.getAccount(), account.getName(), (account.getBalance().add(putBalanceDTO.getBalance())), account.getPoint(), account.getStatus());
        accountRepository.save(account1);
        ModelMapper modelMapper = new ModelMapper();
        AccountGetDTO accountGetDTO = modelMapper.map(account, AccountGetDTO.class);
        logger.info("updating new balance");
        return accountGetDTO;
    }

    public AccountGetDTO updateStatus(AccountPutStatus accountPutStatus) {
        Account account = accountRepository.findByAccount(accountPutStatus.getAccount());
        Account account1 = new Account(account.getId(), accountPutStatus.getAccount(), account.getName(), account.getBalance(), account.getPoint(), accountPutStatus.getStatus());
        checkStatus(account.getStatus());
        accountRepository.save(account1);
        ModelMapper modelMapper = new ModelMapper();
        AccountGetDTO accountGetDTO = modelMapper.map(account, AccountGetDTO.class);
        logger.info("Updated status Account of " + accountPutStatus.getAccount());
        return accountGetDTO;
    }

}
