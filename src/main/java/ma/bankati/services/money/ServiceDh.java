package ma.bankati.services.money;

import org.springframework.stereotype.Service;

@Service("DH")
public class ServiceDh implements IMoneyService {
    @Override
    public double convertir(double montant) {
        return montant; // Pas de conversion
    }
}
