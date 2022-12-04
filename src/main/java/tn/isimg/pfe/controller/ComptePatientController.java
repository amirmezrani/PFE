package tn.isimg.pfe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.service.ComptePatientService;

import javax.validation.Valid;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("/api/comptePatients")
public class ComptePatientController {


    @Autowired
    ComptePatientService comptePatientService;



    // fiind all comptePatient
    @GetMapping
    public List<ComptePatient> findAllComptePatient()
    {
        return comptePatientService.getAllComptePatient();
    }

    // fiind  comptePatient By Id
    @GetMapping("/{id}")
    public ComptePatient findComptePatient(@PathVariable(value = "id") Long idComptePatient){
        return comptePatientService.getComptePatientById(idComptePatient);

    }

    // fiind  comptePatient By email
    @GetMapping("/email")
    public ComptePatient findComptePatientByEmail(@RequestParam(value = "email") String email ){
        return comptePatientService.getCompteByEmail(email);

    }


//    @GetMapping("/{id}/patients")
//    public Set<Patient> findAllMembresFamille(@PathVariable(value = "id") Long id){
//        return comptePatientService.getAllMembresFamille(id);
//    }

    // Creation Compte Patient
    @PostMapping
    public ComptePatient creerComptePatient(@Valid @RequestBody ComptePatient comptePatient){
        return comptePatientService.creerComptePatient(comptePatient);
    }

    // Update Compte Patient
        @PutMapping("/{id}")
        public ComptePatient updateComptePatient(@PathVariable(value = "id") Long idComptePatient,
                @Valid @RequestBody ComptePatient comptePatient){
            return comptePatientService.updateCompte(idComptePatient,comptePatient);
    }


    @PutMapping("/{id}/motDePasse")
    public ComptePatient modifierMotDePasseComptePatient(@PathVariable(value = "id") Long id,
                                                         @Valid @RequestBody MotDePasseInfo motDePasseInfo){
            return comptePatientService.modifierMotDePasseComptePatient(id,motDePasseInfo);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComptePatient(@PathVariable(value = "id") Long idComptePatient){
        return comptePatientService.deleteCompte(idComptePatient);
    }
}
