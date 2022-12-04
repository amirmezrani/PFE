package tn.isimg.pfe.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.service.PatientService;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/comptePatients")
public class PatientController {



    @Autowired
    PatientService patientService;


    // Get  Patient By Id
    @GetMapping("/patients/{id}")
    public Patient findPatient(@PathVariable(value = "id") Long idPatient){
        return patientService.getPatientById(idPatient);
    }

    @GetMapping("/{idComptePatient}/patients")
    public Set<Patient> findAllMembresFamille(@PathVariable(value = "idComptePatient") Long idComptePatient){
        return patientService.getAllMembresFamilleByComptePatient(idComptePatient);
    }

    //Creer Patient By Id Compte Patient
    @PostMapping("/{idComptePatient}/patients")
    public Patient creerMembreFamille(@PathVariable (value = "idComptePatient") Long idComptePatient,
                                      @Valid @RequestBody Patient patient ) {
            return patientService.creerMembreFamille(idComptePatient, patient);
    }

    @PutMapping("/patients/{id}")
    public Patient updatePatient(@PathVariable (value = "id") Long idPatient,
                                 @Valid @RequestBody Patient patient){
        return patientService.updatePatientById(idPatient, patient);
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable (value = "id") Long idPatient) {
        return patientService.deletePatientById(idPatient);
    }
}
