package refactoring.strategy;

import refactoring.entity.Account;

public interface TransferStrategy {
    double calculateFee(double amount);
    void executeRouting(Account sender, Account receiver, double amount);
}
