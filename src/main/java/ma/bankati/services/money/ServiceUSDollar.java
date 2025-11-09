package ma.bankati.services.money;

import org.springframework.stereotype.Service;

@Service("$")
public class ServiceUSDollar implements IMoneyService {
    @Override
    public double convertir(double montant) {
        return montant * 0.10; // 1 DH = 0.10 $
    }
}
