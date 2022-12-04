package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Specialite;
import tn.isimg.pfe.service.SpecialiteService;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SpecialiteController {

    @Autowired
    SpecialiteService specialiteService;

    // fiind all Specialité
    @GetMapping("/specialites")
    public List<Specialite> findAllspecialite()
    {
        return specialiteService.findAll();
    }

    // fiind  Specialité By Id
    @GetMapping("/specialites/{id}")
    public Specialite findSpecialite(@PathVariable(value = "id") Long id){
        return specialiteService.findSpecialiteById(id);
    }

    // Creation specialité
    @PostMapping("/specialites")
    public Specialite creationSpecialite(@Valid @RequestBody Specialite specialite){
        return specialiteService.creerSpecialite(specialite);
    }

    // Update Specialité
    @PutMapping("/administrateurs/specialites/{id}")
    public Specialite updateSpecialite(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody Specialite specialite){
        return specialiteService.updateSpecialiteById(id, specialite);
    }

    // Delete Specialité
    @DeleteMapping("/administrateurs/specialites/{id}")
    public ResponseEntity<?> deleteSpecialite(@PathVariable(value = "id") Long id){
        return specialiteService.deleteSpecialiteById(id);
    }
}
