package refactoring.strategy.impl;

import org.springframework.stereotype.Component;
import refactoring.entity.Account;
import refactoring.strategy.TransferStrategy;

@Component("INTERNATIONAL")
public class InternationalTransferStrategy implements TransferStrategy {
    @Override
    public double calculateFee(double amount) {
        return amount * 0.03 + 50000;
    }

    @Override
    public void executeRouting(Account sender, Account receiver, double amount) {
        System.out.println("[International SWIFT Route] Connecting to SWIFT API...");
    }
}
