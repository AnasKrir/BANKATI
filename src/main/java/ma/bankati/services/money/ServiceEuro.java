package ma.bankati.services.money;

import org.springframework.stereotype.Service;

@Service("€")
public class ServiceEuro implements IMoneyService {
    @Override
    public double convertir(double montant) {
        return montant * 0.096; // 1 DH = 0.096 €
    }
}
