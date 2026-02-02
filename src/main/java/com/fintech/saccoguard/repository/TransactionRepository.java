package com.fintech.saccoguard.repository;

import com.fintech.saccoguard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // This gives us save(), findById(), and findAll() automatically!
}