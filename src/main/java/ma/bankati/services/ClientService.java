package ma.bankati.services;

import ma.bankati.entities.Client;
import ma.bankati.entities.Compte;
import ma.bankati.entities.enums.Devise;
import ma.bankati.repositories.ClientRepository;
import ma.bankati.repositories.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompteRepository compteRepository;

    public Client ajouterClient(Client client) {
        client.setCreatedAt(LocalDate.now());
        Client savedClient = clientRepository.save(client);

        // Création automatique du compte
        Compte compte = new Compte();
        compte.setClient(savedClient);
        compte.setSolde(1000.0);
        compte.setDevise(Devise.DH);
        compte.setDateCreation(LocalDate.now());
        compteRepository.save(compte);

        return savedClient;
    }

    public Optional<Client> findByUsernameAndPassword(String username, String password) {
        return clientRepository.findByUsernameAndPassword(username, password);
    }

    public void supprimerClient(Long id) {
        clientRepository.deleteById(id);
    }
}
