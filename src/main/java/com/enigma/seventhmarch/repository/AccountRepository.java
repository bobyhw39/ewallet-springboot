package com.enigma.seventhmarch.repository;

import com.enigma.seventhmarch.dto.AccountGetDTO;
import com.enigma.seventhmarch.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccount(String account);

    List<Account> findByName(String name);
}
