package com.fintech.saccoguard.controller;

import com.fintech.saccoguard.service.LiquidityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sacco")
public class LiquidityController {

    @Autowired
    private LiquidityService liquidityService;

    @GetMapping("/status")
    public String getLiquidityStatus() {
        return liquidityService.checkLiquidityStatus();
    }

    @GetMapping("/simulate-shock")
    public String simulateShock() {
        // SACCO has 1,000,000 cash, 800,000 expected withdrawals, 1.5x shock factor
        return liquidityService.runShockSimulation(1000000.0, 800000.0, 1.5);
    }
}