package com.enigma.seventhmarch.repository;

import com.enigma.seventhmarch.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAndTransactionDate(String from, Date date);
}
