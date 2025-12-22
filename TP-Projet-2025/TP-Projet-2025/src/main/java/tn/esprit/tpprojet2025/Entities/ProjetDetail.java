package tn.esprit.tpprojet2025.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjetDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProjetDetail;
    String description;
    String technologie;
    Long Cout;
    LocalDate dateDebut;
    @OneToOne(mappedBy = "projetDetail")
    Projet projet;

    public Long getIdProjetDetail() {
        return idProjetDetail;
    }

    public void setIdProjetDetail(Long idProjetDetail) {
        this.idProjetDetail = idProjetDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologie() {
        return technologie;
    }

    public void setTechnologie(String technologie) {
        this.technologie = technologie;
    }

    public Long getCout() {
        return Cout;
    }

    public void setCout(Long cout) {
        Cout = cout;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }
}
