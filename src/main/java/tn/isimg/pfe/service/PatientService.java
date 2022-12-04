package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.repository.PatientRepository;

import java.util.Set;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ComptePatientRepository comptePatientRepository ;

    @Autowired
    ComptePatientService comptePatientService;

    // Get  Patient By Id
    public Patient getPatientById(Long idPatient){
        return patientRepository.findById(idPatient).
                orElseThrow(() -> new ResourceNotFoundException("id Patient " + idPatient + " not found"));
    }

    public Set<Patient> getAllMembresFamilleByComptePatient(Long idComptePatient){

        ComptePatient comptePatient=comptePatientService.getComptePatientById(idComptePatient);
        return patientRepository.findByComptePatient(comptePatient);

    }

    //Creer Patient By Id Compte Patient
    public Patient creerMembreFamille(Long idComptePatient, Patient patient ) {

        ComptePatient comptePatient=comptePatientService.getComptePatientById(idComptePatient);
        patient.setComptePatient(comptePatient);
        return patientRepository.save(patient);

    }


    public Patient updatePatientById(Long idPatient, Patient patientRequest){

            Patient patient= getPatientById(idPatient);

            patient.setNom(patientRequest.getNom());
            patient.setPrenom(patientRequest.getPrenom());
            patient.setAdresse(patientRequest.getAdresse());
            patient.setTelephone(patientRequest.getTelephone());
            patient.setGenre(patientRequest.getGenre());
            patient.setDateDeNaissance(patientRequest.getDateDeNaissance());
            return patientRepository.save(patient);
    }


    public ResponseEntity<?> deletePatientById(Long idPatient) {
        Patient patient= getPatientById(idPatient);
        patientRepository.delete(patient);
        return ResponseEntity.ok().build(); }
}
