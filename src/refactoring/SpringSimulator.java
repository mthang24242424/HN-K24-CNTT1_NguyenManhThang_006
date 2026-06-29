package refactoring;

import refactoring.controller.TransferController;
import refactoring.dto.TransferRequest;
import refactoring.entity.Account;
import refactoring.service.NotificationService;
import refactoring.service.impl.PushNotificationService;
import refactoring.service.impl.SmsNotificationService;
import refactoring.service.impl.TransferServiceImpl;
import refactoring.strategy.TransferStrategy;
import refactoring.strategy.TransferStrategyRegistry;
import refactoring.strategy.impl.DomesticTransferStrategy;
import refactoring.strategy.impl.InternalTransferStrategy;
import refactoring.strategy.impl.InternationalTransferStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * SpringSimulator simulates the Spring ApplicationContext.
 * It demonstrates Dependency Injection, component scanning, and controller routing.
 */
public class SpringSimulator {
    public static void main(String[] args) {
        System.out.println("=== Spring Boot Context Initialization ===");

        // 1. Simulating Component Scan & Bean Instantiation for Strategies
        TransferStrategy internal = new InternalTransferStrategy();
        TransferStrategy domestic = new DomesticTransferStrategy();
        TransferStrategy international = new InternationalTransferStrategy();

        Map<String, TransferStrategy> scannedStrategies = new HashMap<>();
        scannedStrategies.put("INTERNAL", internal);
        scannedStrategies.put("DOMESTIC_BANK", domestic);
        scannedStrategies.put("INTERNATIONAL", international);

        // 2. Instantiate and Autowire Registry bean
        TransferStrategyRegistry strategyRegistry = new TransferStrategyRegistry(scannedStrategies);
        System.out.println("[Spring] Registered TransferStrategy Registry with scanned strategies.");

        // 3. Instantiate Notification Services
        NotificationService smsService = new SmsNotificationService();
        NotificationService pushService = new PushNotificationService();
        System.out.println("[Spring] Registered NotificationServices: 'sms', 'push'.");

        // 4. Instantiate and Autowire TransferServiceImpl (injecting registry & default sms service)
        TransferServiceImpl transferService = new TransferServiceImpl(strategyRegistry, smsService);
        System.out.println("[Spring] Registered TransferServiceImpl with SMS as default notification service.");

        // 5. Instantiate and Autowire TransferController
        TransferController controller = new TransferController(transferService);
        System.out.println("[Spring] Registered TransferController REST Endpoint. Context initialized successfully!\n");

        // --- Run Tests ---

        // Test 1: Send a REST Domestic Bank Transfer Request
        System.out.println("--- Test 1: Simulated HTTP POST /transfer (Domestic Bank, SMS Notification) ---");
        TransferRequest request1 = new TransferRequest("ACC001", "ACC002", 10000.0, "DOMESTIC_BANK");
        String response1 = controller.executeTransfer(request1);
        System.out.println("HTTP Response: " + response1);
        printAccountBalances(controller);

        // Test 2: Switch notification type to Push Notification dynamically
        System.out.println("\n--- Test 2: Switching Notification Service to Push Notification dynamically ---");
        transferService.setNotificationService(pushService);
        System.out.println("[Spring] Injected PushNotificationService into TransferServiceImpl.");

        // Send a REST Internal Transfer Request
        System.out.println("--- Test 2.1: Simulated HTTP POST /transfer (Internal System, Push Notification) ---");
        TransferRequest request2 = new TransferRequest("ACC001", "ACC002", 5000.0, "INTERNAL");
        String response2 = controller.executeTransfer(request2);
        System.out.println("HTTP Response: " + response2);
        printAccountBalances(controller);

        // Test 3: Add new transfer type (e.g. MOMO) dynamically
        System.out.println("\n--- Test 3: Dynamically registering MOMO strategy at runtime ---");
        TransferStrategy momoStrategy = new TransferStrategy() {
            @Override
            public double calculateFee(double amount) {
                return amount * 0.005 + 2000; // 0.5% fee + 2000 VND flat fee
            }

            @Override
            public void executeRouting(Account sender, Account receiver, double amount) {
                System.out.println("[MoMo Wallet Route] Connecting to MoMo E-Wallet Gateway API...");
            }
        };

        // Register the new strategy into the bean registry
        strategyRegistry.register("MOMO", momoStrategy);
        System.out.println("[Registry] Registered 'MOMO' strategy successfully.");

        // Send a REST MOMO Transfer Request
        System.out.println("--- Test 3.1: Simulated HTTP POST /transfer (MOMO E-Wallet, Push Notification) ---");
        TransferRequest request3 = new TransferRequest("ACC001", "ACC002", 20000.0, "MOMO");
        String response3 = controller.executeTransfer(request3);
        System.out.println("HTTP Response: " + response3);
        printAccountBalances(controller);
    }

    private static void printAccountBalances(TransferController controller) {
        Account alice = controller.getAccountFromDb("ACC001");
        Account bob = controller.getAccountFromDb("ACC002");
        System.out.println("State in DB -> Alice: " + alice.getBalance() + " | Bob: " + bob.getBalance());
    }
}
