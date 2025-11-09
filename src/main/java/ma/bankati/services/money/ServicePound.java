package ma.bankati.services.money;

import org.springframework.stereotype.Service;

@Service("£")
public class ServicePound implements IMoneyService {
    @Override
    public double convertir(double montant) {
        return montant * 0.083; // 1 DH = 0.083 £
    }
}
