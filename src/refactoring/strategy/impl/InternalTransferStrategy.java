package refactoring.strategy.impl;

import org.springframework.stereotype.Component;
import refactoring.entity.Account;
import refactoring.strategy.TransferStrategy;

@Component("INTERNAL")
public class InternalTransferStrategy implements TransferStrategy {
    @Override
    public double calculateFee(double amount) {
        return 0;
    }

    @Override
    public void executeRouting(Account sender, Account receiver, double amount) {
        System.out.println("[Internal Route] Processing internal system transfer...");
    }
}
