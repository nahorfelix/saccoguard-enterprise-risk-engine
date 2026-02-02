package com.fintech.saccoguard.controller;

import com.fintech.saccoguard.model.Transaction;
import com.fintech.saccoguard.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository repository;

    @GetMapping("/add-sample")
    public String addSampleData() {
        saveTransaction("Felix", 5000.0, "DEPOSIT");
        return "Deposit of 5,000 saved!";
    }

    @GetMapping("/withdraw-sample")
    public String withdrawSampleData() {
        saveTransaction("Felix", 2000.0, "WITHDRAWAL");
        return "Withdrawal of 2,000 saved!";
    }

    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    // Helper method to keep code clean
    private void saveTransaction(String name, Double amount, String type) {
        Transaction t = new Transaction();
        t.setMemberName(name);
        t.setAmount(amount);
        t.setType(type);
        t.setTransactionDate(LocalDateTime.now());
        repository.save(t);
    }
}