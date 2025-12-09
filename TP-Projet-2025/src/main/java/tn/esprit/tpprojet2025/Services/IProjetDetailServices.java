package tn.esprit.tpprojet2025.Services;

import tn.esprit.tpprojet2025.Entities.ProjetDetail;

import java.util.List;

public interface IProjetDetailServices {
    ProjetDetail AjouterProjetDetail(ProjetDetail projetDetail);
    List<ProjetDetail> afficherListeProjetDetail();
    ProjetDetail afficherProjetDetailSelonID(long idProjetDetail);
    ProjetDetail modifierProjetDetail(ProjetDetail projetDetail);
    void supprimerProjetDetail(long idProjetDetail);







}
