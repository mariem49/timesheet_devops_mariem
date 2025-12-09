package tn.esprit.tpprojet2025.Entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProjet;
    String nomProjet;
    @Enumerated(EnumType.STRING)
    TypeProjet typeProjet;
    @OneToOne(cascade = CascadeType.ALL)
    ProjetDetail projetDetail;
    @ManyToMany
    Set<Equipe> equipes = new HashSet<Equipe>();

    public Long getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Long idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public TypeProjet getTypeProjet() {
        return typeProjet;
    }

    public void setTypeProjet(TypeProjet typeProjet) {
        this.typeProjet = typeProjet;
    }

    public ProjetDetail getProjetDetail() {
        return projetDetail;
    }

    public void setProjetDetail(ProjetDetail projetDetail) {
        this.projetDetail = projetDetail;
    }

    public Set<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(Set<Equipe> equipes) {
        this.equipes = equipes;
    }
}
