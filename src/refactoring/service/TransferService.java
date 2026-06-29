package refactoring.service;

import refactoring.entity.Account;
import refactoring.entity.Transaction;

public interface TransferService {
    Transaction processTransfer(Account sender, Account receiver, double amount, String transferType);
}
