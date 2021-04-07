package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Specialite;
import tn.isimg.pfe.model.Ville;
import tn.isimg.pfe.service.VilleService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/villes")
public class VilleController {

    @Autowired
    VilleService villeService;

    // Creation Ville
    @PostMapping
    public Ville creerVille(@Valid @RequestBody Ville ville){
        return villeService.creerS(ville);
    }

    @GetMapping
    public List<Ville> findAllVille(){
        return villeService.getAllVille();
    }
}
