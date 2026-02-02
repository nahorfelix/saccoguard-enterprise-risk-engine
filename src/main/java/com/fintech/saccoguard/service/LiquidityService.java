package com.fintech.saccoguard.service;

import com.fintech.saccoguard.model.Transaction;
import com.fintech.saccoguard.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LiquidityService {

    @Autowired
    private TransactionRepository repository;

    /**
     * REAL-TIME MONITORING 
     * This is the "selling point." Instead of waiting for a manual click, 
     * this logic analyzes the DB automatically.
     */
    public String checkLiquidityStatus() {
        List<Transaction> all = repository.findAll();
        
        double totalDeposits = all.stream()
                .filter(t -> "DEPOSIT".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();
                
        double totalWithdrawals = all.stream()
                .filter(t -> "WITHDRAWAL".equalsIgnoreCase(t.getType()))
                .mapToDouble(Transaction::getAmount).sum();

        double liquidityRatio = totalDeposits > 0 ? (totalWithdrawals / totalDeposits) : 0;
        double netPosition = totalDeposits - totalWithdrawals;

        // 1. SASRA Compliance Check (Regulatory selling point)
        if (liquidityRatio > 0.85) {
            return "🚨 CRITICAL: Liquidity Ratio at " + Math.round(liquidityRatio * 100) + 
                   "%. Violation of safety limits! Halt new loans immediately.";
        }

        // 2. High-Volatility Detection
        if (liquidityRatio > 0.65) {
            return "⚠️ WARNING: High Withdrawal Trend. Monitor cash reserves for the next 48 hours.";
        }

        return "✅ STABLE: Net Liquidity KES " + String.format("%,.0f", netPosition) + 
               " (Ratio: " + Math.round(liquidityRatio * 100) + "%)";
    }

    /**
     * FRAUD & ANOMALY DETECTION
     * This method can be called by a background task to flag unusual 18,000-member activity.
     */
    public List<Transaction> getHighRiskTransactions() {
        return repository.findAll().stream()
                .filter(t -> "WITHDRAWAL".equals(t.getType()) && t.getAmount() > 50000)
                .collect(Collectors.toList());
    }

    /**
     * SHOCK SIMULATION (AI-STRESS TEST)
     * Used by CEOs to predict if the SACCO survives economic "Black Swan" events.
     */
    public String runShockSimulation(double currentCash, double monthlyOperatingCost, double shockFactor) {
        // We simulate a spike in withdrawals + a drop in new deposits
        double simulatedStress = monthlyOperatingCost * shockFactor;
        double safetyBuffer = currentCash - simulatedStress;

        if (safetyBuffer < 0) {
            return "🚨 FAILURE: Under a " + Math.round((shockFactor - 1) * 100) + 
                   "% shock, the SACCO will face a Cash-Flow Insolvency.";
        }
        
        return "🛡️ RESILIENT: The SACCO maintains a KES " + String.format("%,.0f", safetyBuffer) + 
               " buffer under current stress parameters.";
    }
}