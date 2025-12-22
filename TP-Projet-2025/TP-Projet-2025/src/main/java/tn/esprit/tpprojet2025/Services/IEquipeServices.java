package tn.esprit.tpprojet2025.Services;

import tn.esprit.tpprojet2025.Entities.Equipe;


import java.util.List;

public interface IEquipeServices {
    Equipe AjouterEquipe(Equipe equipe);
    List<Equipe> afficherListeEquipes();
    Equipe afficherEquipeSelonID(long idEquipe);
    Equipe modifierEquipe(Equipe equipe);
    void supprimerEquipe(long idEquipe);
}
