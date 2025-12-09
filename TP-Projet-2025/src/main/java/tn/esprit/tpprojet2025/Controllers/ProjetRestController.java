package tn.esprit.tpprojet2025.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpprojet2025.Entities.Projet;
import tn.esprit.tpprojet2025.Services.IProjetServices;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Projet")
@Tag(name = "Gestion Projets")
public class ProjetRestController {

    @Autowired
    private IProjetServices projetServices;

    @Operation(description = "Ajout d'un projet")
    @PostMapping("/addProjet")
    Projet addProjet(@RequestBody Projet projet) {return projetServices.AjouterProjet(projet);}

    @Operation(description = "Affichage des projets")
    @GetMapping("/getAllProjets")
    List<Projet> getALlProjets(){
        return projetServices.afficherListeProjets();
    }

    @Operation(description = "Affichage d'un projet selon l'ID")
    @GetMapping("/getProjetById/{idP}")
    Projet getProjetById(@PathVariable("idP") long idProjet) {return projetServices.afficherProjetSelonID(idProjet);}

    @Operation(description = "Modification d'un projet")
    @PutMapping("/updateProjet")
    Projet updateProjet( @RequestBody Projet projet) {
        return projetServices.modifierProjet(projet);
    }

    @Operation(description = "Suppression d'un projet")
    @DeleteMapping("/deleteProjet/{idP}")
    void deleteProjet(@PathVariable("idP")long idProjet) {
        projetServices.supprimerProjet(idProjet);
    }

    @PutMapping("/affecterProjetDetailAProjet/{idP}/{idPDetail}")
    public  void  affecterProjetADetailProjet(@PathVariable("idP") long idProjet,
                                              @PathVariable("idPDetail")
                                              long idPDetail){
        projetServices.assignProjetDetailToProjet(idProjet,idPDetail);
    }

    @PutMapping("/affecterProjetAEquipe/{idP}/{idEq}")
    public  void  affecterProjetAEquipe(@PathVariable("idP") long idProjet,
                                        @PathVariable("idEq") long idEquipe){
        projetServices.assignProjetToEquipe(idProjet,idEquipe);
    }

    @PostMapping("/ajouterProjetEtAffecter/{idPdetail}")
    public Projet ajouterProjetEtAffecter(@RequestBody Projet projet,@PathVariable("idPdetail") long IdProjetDetail){
        return  projetServices.addProjetAndAssignProjetDetailToProjet(IdProjetDetail, projet);
    }


}
