package ma.bankati.services.money;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MoneyServiceFactory {
    public static IMoneyService getService(String devise) {
        return switch (devise) {
            case "€" -> new ServiceEuro();
            case "$" -> new ServiceUSDollar();
            case "£" -> new ServicePound();
            default -> new ServiceDh();
        };
    }
}

