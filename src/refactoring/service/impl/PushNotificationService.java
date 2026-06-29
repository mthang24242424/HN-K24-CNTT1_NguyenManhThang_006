package refactoring.service.impl;

import org.springframework.stereotype.Service;
import refactoring.entity.Account;
import refactoring.service.NotificationService;

@Service("push")
public class PushNotificationService implements NotificationService {
    @Override
    public void sendNotification(Account account, String message) {
        System.out.println("[Push Notification Service] Sending Push Notification to " + account.getId() + " about transaction: " + message);
    }
}
