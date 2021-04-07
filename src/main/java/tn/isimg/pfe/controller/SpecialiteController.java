package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Specialite;
import tn.isimg.pfe.service.SpecialiteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/specialites")
public class SpecialiteController {

    @Autowired
    SpecialiteService specialiteService;

    // fiind all Specialité
    @GetMapping
    public List<Specialite> findAllspecialite()
    {
        return specialiteService.findAll();
    }

    // fiind  Specialité By Id
    @GetMapping("/{id}")
    public Specialite findSpecialite(@PathVariable(value = "id") Long id){
        return specialiteService.findSpecialiteById(id);
    }

    // Creation specialité
    @PostMapping
    public Specialite creerSpecialite(@Valid @RequestBody Specialite specialite){
        return specialiteService.creer(specialite);
    }

    // Update Specialité
    @PutMapping("/{id}")
    public Specialite updateComptePatient(@PathVariable(value = "id") Long id,
                                          @Valid @RequestBody Specialite specialite){
        return specialiteService.update(id, specialite);
    }

    // Delete Specialité
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpecialite(@PathVariable(value = "id") Long id){
        return specialiteService.deleteSpecialiteById(id);
    }
}
