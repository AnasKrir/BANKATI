package ma.bankati.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.bankati.entities.enums.StatutCredit;

import java.time.LocalDate;

@Entity
@Table(name = "credits")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double capital;
    private int dureeMois;
    private Double tauxMensuel;
    private Double mensualite;
    private LocalDate dateDemande;

    @Enumerated(EnumType.STRING)
    private StatutCredit statut;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    public void calculerMensualite() {
        if (capital != null && tauxMensuel != null && dureeMois > 0) {
            double r = tauxMensuel / 100;
            double m = (capital * r) / (1 - Math.pow(1 + r, -dureeMois));
            this.mensualite = Math.round(m * 100.0) / 100.0; // 🔁 arrondi à 2 chiffres
        }
    }

}
