package tn.esprit.tpprojet2025.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpprojet2025.Entities.Equipe;
import tn.esprit.tpprojet2025.Services.IEquipeServices;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Equipe")
@Tag(name = "Gestion Equipes")
public class EquipeRestController {

    @Autowired
    IEquipeServices equipeService;

    @Operation(description = "Ajout d'une équipe")
    @PostMapping("/addEquipe")
    Equipe addEquipe(@RequestBody Equipe equipe) {return equipeService.AjouterEquipe(equipe);}

    @Operation(description = "Affichage des équipes")
    @GetMapping("/getAllEquipes")
    List<Equipe> getALlEquipes(){return equipeService.afficherListeEquipes();}

    @Operation(description = "Affichage d'une équipe selon l'ID")
    @GetMapping("/getEquipeById/{idEq}")
    Equipe getEquipeById(@PathVariable("idEq") long idEquipe) {return equipeService.afficherEquipeSelonID(idEquipe);}

    @Operation(description = "Modification d'une équipe")
    @PutMapping("/updateEquipe")
    Equipe updateEquipe( @RequestBody Equipe equipe ) {return equipeService.modifierEquipe(equipe);}

    @Operation(description = "Suppression d'une équipe")
    @DeleteMapping("/deleteEquipe/{idEq}")
    void deleteEquipe(@PathVariable("idEq")long idEquipe) {equipeService.supprimerEquipe(idEquipe);}

}
