package refactoring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import refactoring.entity.Account;
import refactoring.entity.Transaction;
import refactoring.service.NotificationService;
import refactoring.service.TransferService;
import refactoring.strategy.TransferStrategy;
import refactoring.strategy.TransferStrategyRegistry;

@Service
public class TransferServiceImpl implements TransferService {
    
    private final TransferStrategyRegistry registry;
    private NotificationService notificationService;

    @Autowired
    public TransferServiceImpl(
            TransferStrategyRegistry registry, 
            @Qualifier("sms") NotificationService notificationService) {
        this.registry = registry;
        this.notificationService = notificationService;
    }

    // Setter to dynamically switch notification service (e.g. from SMS to Push)
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public NotificationService getNotificationService() {
        return this.notificationService;
    }

    @Override
    public Transaction processTransfer(Account sender, Account receiver, double amount, String transferType) {
        // Retrieve appropriate strategy from registry
        TransferStrategy strategy = registry.getStrategy(transferType);

        // Calculate transaction fee
        double fee = strategy.calculateFee(amount);

        // Balance validation
        if (sender.getBalance() < (amount + fee)) {
            throw new RuntimeException("Insufficient balance");
        }

        // Execute API Connection and routing
        strategy.executeRouting(sender, receiver, amount);

        // Deduct balance
        sender.setBalance(sender.getBalance() - amount - fee);
        receiver.setBalance(receiver.getBalance() + amount);

        // Send transaction notification
        notificationService.sendNotification(sender, 
            "Transaction of " + amount + " to " + receiver.getId() + " was successful. Fee: " + fee);

        return new Transaction(sender, receiver, amount, fee, "SUCCESS");
    }
}
