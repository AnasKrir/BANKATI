package ma.bankati.services;

import ma.bankati.entities.Utilisateur;
import ma.bankati.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Optional<Utilisateur> login(String username, String password) {
        return utilisateurRepository.findByUsernameAndPassword(username, password);
    }
}
