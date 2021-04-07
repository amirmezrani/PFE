package tn.isimg.pfe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.repository.PatientRepository;
import tn.isimg.pfe.service.PatientService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class PatientController {



    @Autowired
    PatientService patientService;

   /* // Get All Patient
    @GetMapping("/comptepatients/{id}/patients")
    public List<Patient> findAllComptePatient(@PathVariable(value = "id") Long idCompte){
        return patientRepository.findByCompteId(idCompte);
    }*/

    // Get  Patient By Id
    @GetMapping("/patients/{id}")
    public Patient findPatient(@PathVariable(value = "id") Long idPatient){
        return patientService.getPatient(idPatient);
    }

    //Creer Patient By Id Compte Patient
    @PostMapping("/comptePatients/{id}/patients")
    public Patient creerMembreFamille(@PathVariable (value = "id") Long idCompte,
                                      @Valid @RequestBody Patient patient ) {
            return patientService.creerMembre(idCompte, patient);
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@PathVariable (value = "id") Long idPatient,
                                 @Valid @RequestBody Patient patient){
        return patientService.update(idPatient, patient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable (value = "id") Long idPatient) {
        return patientService.delete(idPatient);
    }
}
