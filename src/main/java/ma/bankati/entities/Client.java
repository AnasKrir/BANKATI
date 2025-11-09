package ma.bankati.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("CLIENT")
@Table(name = "clients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Client extends Utilisateur {

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Compte compte;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Credit> credits;
}
