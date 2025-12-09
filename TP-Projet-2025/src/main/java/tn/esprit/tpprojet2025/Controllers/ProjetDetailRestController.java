package tn.esprit.tpprojet2025.Controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpprojet2025.Entities.ProjetDetail;
import tn.esprit.tpprojet2025.Services.IProjetDetailServices;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/ProjetDetail")
@Tag(name = "Gestion Projets Details")
public class ProjetDetailRestController {

    @Autowired
    private IProjetDetailServices projetDetailServices;

    @Operation(description = "Ajout d'un projet détail")
    @PostMapping("/addProjetDetail")
    ProjetDetail addProjetDetail(@RequestBody ProjetDetail projetDetail) {return projetDetailServices.AjouterProjetDetail(projetDetail);}

    @Operation(description = "Affichage des projets détails")
    @GetMapping("/getAllProjetsDetail")
    List<ProjetDetail> getALlProjetDetails(){
        return projetDetailServices.afficherListeProjetDetail();
    }

    @Operation(description = "Affichage d'un projet détail selon l'ID")
    @GetMapping("/getProjetDetailById/{idProjetD}")
    ProjetDetail getProjetDetailById(@PathVariable("idProjetD") long idProjetDetail) {return projetDetailServices.afficherProjetDetailSelonID(idProjetDetail);}

    @Operation(description = "Modification d'un projet détail")
    @PutMapping("/updateProjetDetail")
    ProjetDetail updateProjetDetail( @RequestBody ProjetDetail projetDetail) {return projetDetailServices.modifierProjetDetail(projetDetail);}

    @Operation(description = "Suppression d'un projet détail")
    @DeleteMapping("/deleteProjetDetail/{idProjetD}")
    void deleteProjet(@PathVariable("idProjetD")long idProjetDetail) {projetDetailServices.supprimerProjetDetail(idProjetDetail);}
}
