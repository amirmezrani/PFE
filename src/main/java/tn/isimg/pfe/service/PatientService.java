package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.repository.PatientRepository;
@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ComptePatientRepository comptePatientRepository ;

    // Get  Patient By Id
    public Patient getPatient(Long idPatient){
        return patientRepository.findById(idPatient).
                orElseThrow(() -> new ResourceNotFoundException("id Patient " + idPatient + " not found"));
    }

    //Creer Patient By Id Compte Patient
    public Patient creerMembre(Long idCompte,Patient patient ) {
        return comptePatientRepository.findById(idCompte).map(comptePatient -> {
            comptePatient.getMembresFamille().add(patient);
            comptePatientRepository.save(comptePatient);
            return patient;
        }).orElseThrow(() -> new ResourceNotFoundException("Compte Patient " + idCompte + " not found"));
    }


    public Patient update(Long idPatient, Patient patientRequest){

        return patientRepository.findById(idPatient).map(patient -> {
            patient.setNom(patientRequest.getNom());
            patient.setPrenom(patientRequest.getPrenom());
            patient.setAdresse(patientRequest.getAdresse());
            patient.setTelephone(patientRequest.getTelephone());
            patient.setGenre(patientRequest.getGenre());
            patient.setDateDeNaissance(patientRequest.getDateDeNaissance());
            return patientRepository.save(patient);
        }).orElseThrow(() -> new ResourceNotFoundException("id Patient " + idPatient + "not found"));

    }


    public ResponseEntity<?> delete(Long idPatient) {
        return patientRepository.findById(idPatient).map(patient -> {
            patientRepository.delete(patient);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " +
                idPatient));
    }
}
