package ma.bankati.repositories;

import ma.bankati.entities.Credit;
import ma.bankati.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Long> {
    List<Credit> findByClient_Id(Long clientId);
    List<Credit> findByClient(Client client);
}
