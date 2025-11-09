package ma.bankati.services;

import ma.bankati.entities.Compte;
import ma.bankati.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompteService {

    @Autowired
    private CompteRepository compteRepository;

    public Optional<Compte> getCompteByClientId(Long idClient) {
        return compteRepository.findByClient_Id(idClient);
    }
}
