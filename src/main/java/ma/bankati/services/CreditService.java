package ma.bankati.services;

import ma.bankati.entities.Credit;
import ma.bankati.entities.enums.StatutCredit;
import ma.bankati.repositories.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditService {

    @Autowired
    private CreditRepository creditRepository;

    public Credit demanderCredit(Credit credit) {
        credit.setDateDemande(LocalDate.now());
        credit.setStatut(StatutCredit.EN_COURS);
        credit.calculerMensualite(); // Assure-toi que cette méthode est bien dans l'entité Credit
        return creditRepository.save(credit);
    }

    public List<Credit> findByClientId(Long clientId) {
        return creditRepository.findByClient_Id(clientId);
    }

    public List<Credit> findAll() {
        return creditRepository.findAll();
    }

    public void supprimerSiEnCours(Long id) {
        creditRepository.findById(id).ifPresent(credit -> {
            if (credit.getStatut() == StatutCredit.EN_COURS) {
                creditRepository.deleteById(id);
            }
        });
    }

    public void approuver(Long id) {
        creditRepository.findById(id).ifPresent(credit -> {
            if (credit.getStatut() == StatutCredit.EN_COURS) {
                credit.setStatut(StatutCredit.APPROUVEE);
                creditRepository.save(credit);
            }
        });
    }

    public void rejeter(Long id) {
        creditRepository.findById(id).ifPresent(credit -> {
            if (credit.getStatut() == StatutCredit.EN_COURS) {
                credit.setStatut(StatutCredit.REJETEE);
                creditRepository.save(credit);
            }
        });
    }
}
