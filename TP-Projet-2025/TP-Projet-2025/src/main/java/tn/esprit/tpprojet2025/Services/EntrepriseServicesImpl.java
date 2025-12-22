package tn.esprit.tpprojet2025.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpprojet2025.Entities.Entreprise;
import tn.esprit.tpprojet2025.Repositories.EntrepriseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EntrepriseServicesImpl implements IEntrepriseServices {

    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @Override
    public Entreprise AjouterEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public List<Entreprise> afficherListeEntreprise() {
        return entrepriseRepository.findAll();
    }

    @Override
    public Entreprise afficherEntrepriseSelonID(long idEntreprise) {
        return entrepriseRepository.findById(idEntreprise).get();
    }

    @Override
    public Entreprise modifierEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public void supprimerEntreprise(long idEntreprise) {
         entrepriseRepository.deleteById(idEntreprise);
    }
}
