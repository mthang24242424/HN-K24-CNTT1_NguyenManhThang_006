package refactoring.service.impl;

import org.springframework.stereotype.Service;
import refactoring.entity.Account;
import refactoring.service.NotificationService;

@Service("sms")
public class SmsNotificationService implements NotificationService {
    @Override
    public void sendNotification(Account account, String message) {
        System.out.println("[SMS Notification Service] Sending SMS to " + account.getPhone() + " about transaction: " + message);
    }
}
