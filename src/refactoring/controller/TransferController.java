package refactoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import refactoring.dto.TransferRequest;
import refactoring.entity.Account;
import refactoring.entity.Transaction;
import refactoring.service.TransferService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TransferController {

    private final TransferService transferService;
    // Mock database representing accounts in our e-wallet system
    private final Map<String, Account> mockDatabase = new HashMap<>();

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
        // Seed mock accounts in database
        mockDatabase.put("ACC001", new Account("ACC001", 1000000, "0912345678"));
        mockDatabase.put("ACC002", new Account("ACC002", 50000, "0987654321"));
    }

    @PostMapping("/transfer")
    public String executeTransfer(@RequestBody TransferRequest request) {
        try {
            Account sender = mockDatabase.get(request.getSenderId());
            Account receiver = mockDatabase.get(request.getReceiverId());

            if (sender == null) {
                return "FAIL: Sender account " + request.getSenderId() + " not found";
            }
            if (receiver == null) {
                return "FAIL: Receiver account " + request.getReceiverId() + " not found";
            }

            Transaction transaction = transferService.processTransfer(
                sender, 
                receiver, 
                request.getAmount(), 
                request.getTransferType()
            );

            return "SUCCESS: Transfer completed. Status: " + transaction.getStatus() 
                + ", Fee: " + transaction.getFee();
        } catch (Exception e) {
            return "FAIL: " + e.getMessage();
        }
    }

    public Account getAccountFromDb(String id) {
        return mockDatabase.get(id);
    }
}
