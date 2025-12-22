package tn.esprit.tpprojet2025.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpprojet2025.Entities.Equipe;
import tn.esprit.tpprojet2025.Repositories.EquipeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipeServicesImpl implements IEquipeServices {

    @Autowired
    private EquipeRepository equipeRepository;

    @Override
    public Equipe AjouterEquipe(Equipe equipe) {
        return equipeRepository.save(equipe) ;
    }

    @Override
    public List<Equipe> afficherListeEquipes() {
        return equipeRepository.findAll();
    }

    @Override
    public Equipe afficherEquipeSelonID(long idEquipe) {
        return equipeRepository.findById(idEquipe).get();
    }

    @Override
    public Equipe modifierEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Override
    public void supprimerEquipe(long idEquipe) {
      equipeRepository.deleteById(idEquipe);
    }
}
