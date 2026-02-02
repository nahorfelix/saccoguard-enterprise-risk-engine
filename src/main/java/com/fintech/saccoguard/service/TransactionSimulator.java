package com.fintech.saccoguard.service;

import com.fintech.saccoguard.model.Transaction;
import com.fintech.saccoguard.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TransactionSimulator {

    @Autowired
    private TransactionRepository repository;

    private final Random random = new Random();
    private final String[] names = {"John Kamau", "Mary Atieno", "David Mutua", "Sarah Hassan", "Felix Omondi", "Esther Wanjiku"};

    // Runs automatically every 5 seconds (5000ms)
    @Scheduled(fixedDelay = 5000)
    public void simulateActivity() {
        Transaction t = new Transaction();
        
        // Randomly pick a name
        t.setMemberName(names[random.nextInt(names.length)]);
        
        // Randomly decide: 70% chance of Deposit, 30% chance of Withdrawal
        boolean isDeposit = random.nextDouble() > 0.3;
        t.setType(isDeposit ? "DEPOSIT" : "WITHDRAWAL");
        
        // Random amount between 500 and 15,000
        double amount = 500 + (14500 * random.nextDouble());
        t.setAmount(Math.round(amount * 100.0) / 100.0); // Round to 2 decimal places
        
        t.setTransactionDate(LocalDateTime.now());
        
        repository.save(t);
        System.out.println("[SIMULATOR] Auto-generated " + t.getType() + " for " + t.getMemberName());
    }
}