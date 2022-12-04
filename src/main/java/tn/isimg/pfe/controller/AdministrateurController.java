package tn.isimg.pfe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.isimg.pfe.model.Administrateur;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.service.AdministrateurService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import tn.isimg.pfe.service.ComptePatientService;
import tn.isimg.pfe.service.PatientService;

@CrossOrigin
@RestController
@RequestMapping("/api/administrateurs")
public class AdministrateurController {

    @Autowired
    AdministrateurService administrateurService;

    @Autowired
    ComptePatientService comptePatientService;

    @Autowired
    PatientService patientService;

    // Get All Administrateur
    @GetMapping
    public List<Administrateur> findAlladministrateur(){
        return administrateurService.findAllAdmin();
    }

    // Get  Administrateur By Id
    @GetMapping("/{id}")
    public Administrateur findAdministrateurById(@PathVariable(value = "id") Long id){
        return administrateurService.findAdministrateurById(id);
    }

    // Get  Administrateur By Email
    @GetMapping("/email")
    public Administrateur findAdministrateurByEmail(@RequestParam(value = "email") String email){
        return administrateurService.getAdminByEmail(email);
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


    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // pour le compte patient !!!!!



    // fiind all comptePatient
    @GetMapping("/comptePatients")
    public List<ComptePatient> findAllComptePatient()
    {
        return comptePatientService.getAllComptePatient();
    }

    // fiind  comptePatient By Id
    @GetMapping("/comptePatients/{id}")
    public ComptePatient findComptePatient(@PathVariable(value = "id") Long idComptePatient){
        return comptePatientService.getComptePatientById(idComptePatient);

    }


    // Creation Compte Patient
    @PostMapping("/comptePatients")
    public ComptePatient creerComptePatient(@Valid @RequestBody ComptePatient comptePatient){
        return comptePatientService.creerComptePatient(comptePatient);
    }

    // Update Compte Patient
    @PutMapping("/comptePatients/{id}")
    public ComptePatient updateComptePatient(@PathVariable(value = "id") Long idComptePatient,
                                             @Valid @RequestBody ComptePatient comptePatient){
        return comptePatientService.updateCompte(idComptePatient,comptePatient);
    }



    @DeleteMapping("/comptePatients/{id}")
    public ResponseEntity<?> deleteComptePatient(@PathVariable(value = "id") Long idComptePatient){
        return comptePatientService.deleteCompte(idComptePatient);
    }

    // pour le patient !!!!!

    // Get  Patient By Id
    @GetMapping("/patients/{id}")
    public Patient findPatient(@PathVariable(value = "id") Long idPatient){
        return patientService.getPatientById(idPatient);
    }

    @GetMapping("/comptePatients/{idComptePatient}/patients")
    public Set<Patient> findAllMembresFamille(@PathVariable(value = "idComptePatient") Long idComptePatient){
        return patientService.getAllMembresFamilleByComptePatient(idComptePatient);
    }

    //Creer Patient By Id Compte Patient
    @PostMapping("/comptePatients/{idComptePatient}/patients")
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
