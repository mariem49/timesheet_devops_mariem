package tn.esprit.tpprojet2025.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpprojet2025.Entities.Entreprise;
import tn.esprit.tpprojet2025.Services.IEntrepriseServices;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Entreprise")
@Tag(name = "Gestion Entreprises")
public class EntrepriseRestController {

    @Autowired
    private IEntrepriseServices entrepriseService;

    @Operation(description = "Ajout d'une entreprise")
    @PostMapping("/addEntreprise")
    Entreprise addEntreprise(@RequestBody Entreprise entreprise) {return entrepriseService.AjouterEntreprise(entreprise);}

    @Operation(description = "Affichage des entreprises")
    @GetMapping("/getAllEntreprises")
    List<Entreprise> getALlEntreprises(){return entrepriseService.afficherListeEntreprise();}

    @Operation(description = "Affichage d'une entreprise selon l'ID")
    @GetMapping("/getEntrepriseById/{idE}")
    Entreprise getEntrepriseById(@PathVariable("idE") long idEntreprise) {return entrepriseService.afficherEntrepriseSelonID(idEntreprise);}

    @Operation(description = "Modification d'une entreprise")
    @PutMapping("/updateEntreprise")
    Entreprise updateEntreprise( @RequestBody Entreprise entreprise) {return entrepriseService.modifierEntreprise(entreprise);}

    @Operation(description = "Suppression d'une entreprise ")
    @DeleteMapping("/deleteEntreprise/{idE}")
    void deleteEntreprise(@PathVariable("idE")long idEntreprise) {entrepriseService.supprimerEntreprise(idEntreprise);}

}
