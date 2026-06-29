package refactoring.strategy.impl;

import org.springframework.stereotype.Component;
import refactoring.entity.Account;
import refactoring.strategy.TransferStrategy;

@Component("DOMESTIC_BANK")
public class DomesticTransferStrategy implements TransferStrategy {
    @Override
    public double calculateFee(double amount) {
        return amount * 0.01;
    }

    @Override
    public void executeRouting(Account sender, Account receiver, double amount) {
        System.out.println("[Domestic Napas Route] Connecting to Napas API...");
    }
}
