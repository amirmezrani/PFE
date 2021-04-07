package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.repository.AdministrateurRepository;
import tn.isimg.pfe.service.AdministrateurService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/administrateurs")
public class AdministrateurController {

    @Autowired
    AdministrateurService administrateurService;

    // Get All Administrateur
    @GetMapping
    public List<Administrateur> findAlladministrateur(){
        return administrateurService.findAllAdmin();
    }

    // Get All Administrateur
    @GetMapping("/{id}")
    public Administrateur findadministrateurById(@PathVariable(value = "id") Long id){
        return administrateurService.findadministrateur(id);
    }

    // creer Utilisateur
    @PostMapping
    public Administrateur creerAdministrateur(@Valid @RequestBody Administrateur administrateur){
        return administrateurService.creer(administrateur);
    }
    // Update administrateur
    @PutMapping("/{id}")
    public Administrateur updateAdministrateur(@PathVariable(value = "id") Long id,
                                 @Valid @RequestBody Administrateur administrateur){
        return administrateurService.updateAdmin(id, administrateur);
    }

    // Delete Utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdministrateur(@PathVariable(value = "id") Long id){
        return administrateurService.deleteAdmin(id);
    }
}
