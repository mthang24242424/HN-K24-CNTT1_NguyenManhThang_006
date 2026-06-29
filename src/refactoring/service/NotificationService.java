package refactoring.service;

import refactoring.entity.Account;

public interface NotificationService {
    void sendNotification(Account account, String message);
}
