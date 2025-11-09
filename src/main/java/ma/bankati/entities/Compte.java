package ma.bankati.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.bankati.entities.enums.Devise;

import java.time.LocalDate;

@Entity
@Table(name = "comptes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double solde;

    @Enumerated(EnumType.STRING)
    private Devise devise;

    private LocalDate dateCreation;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client client;
}
