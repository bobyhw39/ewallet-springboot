package com.enigma.seventhmarch;

import com.enigma.seventhmarch.dto.*;
import com.enigma.seventhmarch.entity.Account;
import com.enigma.seventhmarch.entity.Transaction;
import com.enigma.seventhmarch.exceptions.BadRequestException;
import com.enigma.seventhmarch.exceptions.NotFoundException;
import com.enigma.seventhmarch.repository.AccountRepository;
import com.enigma.seventhmarch.repository.TransactionRepository;
import com.enigma.seventhmarch.services.AccountServices;
import com.enigma.seventhmarch.services.TransactionServices;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class TransactionServiceTest {

    @TestConfiguration
    static class TransactionServiceContextConfiguration{

        @Bean
        public TransactionServices transactionServices(){
            return new TransactionServices();
        }

        @Bean
        public AccountServices accountServices(){
            return new AccountServices();
        }
    }
    @Autowired
    private TransactionServices transService;
    @Autowired
    private AccountServices accService;
    @MockBean
    private TransactionRepository repo;
    @MockBean
    private AccountRepository repoAccount;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Account account = new Account(99l,"123123", "boby", new BigDecimal(1000000), new BigDecimal(1000), 0);
        Account accountTo = new Account(100l,"123456", "boby2", new BigDecimal(100000), new BigDecimal(0), 0);
//        AccountGetDTO account = accountServices.getAccount(postBalanceDTO.getAccount());
        List<AccountGetDTO> accountGetDTOList = Lists.newArrayList();
        List<Account> accountList = Lists.newArrayList();
        accountList.add(account);
        ModelMapper modelMapper = new ModelMapper();
        AccountGetDTO accountGetDTO = modelMapper.map(account, AccountGetDTO.class);
        accountGetDTOList.add(accountGetDTO);
        Mockito.when(repoAccount.findByAccount("123123")).thenReturn(account);
        Mockito.when(repoAccount.save(account)).thenReturn(account);
        Mockito.when(repoAccount.save(accountTo)).thenReturn(accountTo);
        Transaction transaction = new Transaction(1l, Date.valueOf("2020-03-08"),"transfer","123123","123456",new BigDecimal(10000),"buat jajan");
        Mockito.when(repo.save(transaction)).thenReturn(transaction);
        AccountGetDTO forTest = new AccountGetDTO(1L,"393939","Bob",new BigDecimal(1000000), new BigDecimal(10000),0);
    }

    @Test
    public void whenPostBalance_thenShouldReturnAccountGetDTO(){
        TransactionPostBalanceDTO dto = new TransactionPostBalanceDTO("123123",new BigDecimal(100000));
        assertEquals(transService.postBalance(dto).getAccount(),"123123");
    }

    @Test
    public void whenPostPointToBalance_thenShouldreturnSuccess(){
        TransactionPostPointToBalanceDTO dto = new TransactionPostPointToBalanceDTO("123123");
        assertEquals(transService.postTransactionPoint(dto),"transfer point from " + dto.getAccount() + " success");
    }

    @Test
    public void whenPostPointToBalance_thenShouldReturnException(){
        TransactionPostPointToBalanceDTO dto = new TransactionPostPointToBalanceDTO(("123456"));
        expectedException.expect(NotFoundException.class);
        transService.postTransactionPoint(dto);
    }

    @Test
    public void whenPostTransfer_thenShouldReturnSuccess(){
        TransactionPostTransferDTO dto = new TransactionPostTransferDTO("123123","123123",new BigDecimal(100000),"buat jajan");
        assertEquals(transService.postTransactionTransfer(dto),"success");
    }
    @Test
    public void whenPostTransfer_thenShouldReturnInsufficient(){
        TransactionPostTransferDTO dto = new TransactionPostTransferDTO("123123","123456",new BigDecimal(1110000),"buat jajan");
        expectedException.expect(BadRequestException.class);
            transService.postTransactionTransfer(dto);
    }

    @Test
    public void whenPostPulsa_thenShouldReturnSuccess(){
        TransactionPostPulsaDTO dto = new TransactionPostPulsaDTO("123123",new BigDecimal(10000),"991");
        assertEquals(transService.postTransactionPulsa(dto),"pulsa to " + dto.getPhonenumber() + " success , you got " + transService.getPoint(dto.getAmount()) + " point");
    }

    @Test
    public void whenPostPulsa_thenShouldReturnInsufficient(){
        TransactionPostPulsaDTO dto = new TransactionPostPulsaDTO("123123",new BigDecimal(2000000),"991");
        expectedException.expect(BadRequestException.class);
        transService.postTransactionPulsa(dto);
    }

    @Test
    public void whenPostAccount_thenShouldReturnAccount(){
        AccountPostDTO dto = new AccountPostDTO("393939","Bob",new BigDecimal(1000000));
        assertEquals(accService.postAccount(dto).getAccount(),"393939");
    }

    @Test
    public void whenPostAccountWith3AccountLength_thenShouldReturnException(){
        AccountPostDTO dto = new AccountPostDTO("39","Bob",new BigDecimal(1000000));
        expectedException.expect(BadRequestException.class);
        accService.postAccount(dto);
    }

}
