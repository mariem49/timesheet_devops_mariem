package tn.esprit.tpprojet2025.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpprojet2025.Entities.ProjetDetail;
import tn.esprit.tpprojet2025.Repositories.ProjetDetailRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class ProjetDetailServicesImpl implements IProjetDetailServices{

    @Autowired
    private ProjetDetailRepository projetDetailRepository;

    @Override
    public ProjetDetail AjouterProjetDetail(ProjetDetail projetDetail) {
        return projetDetailRepository.save(projetDetail);
    }

    @Override
    public List<ProjetDetail> afficherListeProjetDetail() {
        return projetDetailRepository.findAll();
    }


    @Override
    public ProjetDetail afficherProjetDetailSelonID(long idProjetDetail) {
        return projetDetailRepository.findById(idProjetDetail).get();
    }

    @Override
    public ProjetDetail modifierProjetDetail(ProjetDetail projetDetail) {
        return projetDetailRepository.save(projetDetail);
    }

    @Override
    public void supprimerProjetDetail(long idProjetDetail) {
          projetDetailRepository.deleteById(idProjetDetail);
    }


}
