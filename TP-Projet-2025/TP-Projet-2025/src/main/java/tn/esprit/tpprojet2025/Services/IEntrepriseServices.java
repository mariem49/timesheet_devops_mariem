package tn.esprit.tpprojet2025.Services;

import tn.esprit.tpprojet2025.Entities.Entreprise;
import tn.esprit.tpprojet2025.Entities.Equipe;
import tn.esprit.tpprojet2025.Entities.Projet;

import java.util.List;

public interface IEntrepriseServices {
    Entreprise AjouterEntreprise(Entreprise entreprise);
    List<Entreprise> afficherListeEntreprise();
    Entreprise afficherEntrepriseSelonID(long idEntreprise);
    Entreprise modifierEntreprise(Entreprise entreprise);
    void supprimerEntreprise(long idEntreprise);
}
