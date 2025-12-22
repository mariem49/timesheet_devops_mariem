package tn.esprit.tpprojet2025.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEquipe;
    String nomEquipe;
    String specialite;
    @ManyToOne(cascade = CascadeType.ALL)
    Entreprise entreprise;
    @ManyToMany(cascade = CascadeType.ALL)
    Set<Projet> projets;

    public Long getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Long idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }

    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Set<Projet> getProjets() {
        return projets;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }
}
