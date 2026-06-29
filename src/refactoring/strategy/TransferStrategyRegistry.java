package refactoring.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TransferStrategyRegistry {
    
    private final Map<String, TransferStrategy> strategies = new ConcurrentHashMap<>();

    @Autowired
    public TransferStrategyRegistry(Map<String, TransferStrategy> injectedStrategies) {
        if (injectedStrategies != null) {
            this.strategies.putAll(injectedStrategies);
        }
    }

    public TransferStrategy getStrategy(String transferType) {
        TransferStrategy strategy = strategies.get(transferType);
        if (strategy == null) {
            throw new RuntimeException("Transfer type not supported: " + transferType);
        }
        return strategy;
    }

    public void register(String transferType, TransferStrategy strategy) {
        strategies.put(transferType, strategy);
    }
}
