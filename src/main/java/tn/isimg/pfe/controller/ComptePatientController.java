package tn.isimg.pfe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.service.ComptePatientService;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/comptePatients")
public class ComptePatientController {


    @Autowired
    ComptePatientService comptePatientService;

   /* // fiind all comptePatient
    @GetMapping("/comptepatients")
    public List<ComptePatient> findAllComptePatient()
    {
        return comptePatientRepository.findByIdAndDate();
    }*/

    // fiind  comptePatient By Id
    @GetMapping("/{id}")
    public ComptePatient findComptePatient(@PathVariable(value = "id") Long id){
        return comptePatientService.getComptePatient(id);

    }


    @GetMapping("/{id}/patients")
    public Set<Patient> findAllMembresFamille(@PathVariable(value = "id") Long id){
        return comptePatientService.getAllMembresFamille(id);
    }

    // Creation Compte Patient
    @PostMapping
    public ComptePatient creerComptePatient(@Valid @RequestBody ComptePatient comptePatient){
        return comptePatientService.creer(comptePatient);
    }

    // Update Compte Patient
        @PutMapping("/{id}")
        public ComptePatient updateComptePatient(@PathVariable(value = "id") Long idCompte,
                @Valid @RequestBody ComptePatient comptePatient){
            return comptePatientService.updateCompte(idCompte,comptePatient);
    }


    @PutMapping("/{id}/motDePasse")
    public ComptePatient modifierMotDePasseComptePatient(@PathVariable(value = "id") Long id,
                                                         @Valid @RequestBody MotDePasseInfo motDePasseInfo){
            return comptePatientService.modifierMotDePasse(id,motDePasseInfo);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComptePatient(@PathVariable(value = "id") Long idCompte){
        return comptePatientService.deleteCompte(idCompte);
    }
}
