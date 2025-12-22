package tn.esprit.tpprojet2025.Services;

import tn.esprit.tpprojet2025.Entities.Projet;

import java.util.List;

public interface IProjetServices {

   Projet AjouterProjet(Projet projet);
   List <Projet> afficherListeProjets();
   Projet afficherProjetSelonID(long idProjet);
   Projet modifierProjet(Projet projet);
   void supprimerProjet(long idProjet);
   void assignProjetDetailToProjet(long idProjet, long idProjetDetail);
   void assignProjetToEquipe(long idProjet, long idEquipe);
   Projet addProjetAndAssignProjetDetailToProjet(long idProjetDetail, Projet projet);
   void UnassignProjetDetailFromProjet(long idProjet);
   void UnassignProjetFromEquipe(long idEquipe, long idProjet);
}
