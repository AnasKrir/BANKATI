package ma.bankati.repositories;

import ma.bankati.entities.Compte;
import ma.bankati.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    Optional<Compte> findByClient_Id(Long clientId);
    Optional<Compte> findByClient(Client client);
}
