package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import tn.isimg.pfe.model.Ville;
import tn.isimg.pfe.service.VilleService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class VilleController {

    @Autowired
    VilleService villeService;

    // Creation Ville
    @PostMapping("/villes")
    public Ville creationVille(@Valid @RequestBody Ville ville){
        return villeService.creerVille(ville);
    }

    @GetMapping("/villes")
    public List<Ville> findAllVille(){
        return villeService.getAllVille();
    }

    @PutMapping("/villes/{id}")
    public Ville updateVille(@PathVariable(value = "id") Long id
                                ,@Valid @RequestBody Ville ville){
        return villeService.updateVilleById(id,ville);
    }
}
