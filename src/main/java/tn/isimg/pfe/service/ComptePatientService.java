package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.exception.MotDePasseException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.repository.ComptePatientRepository;

import java.util.Set;

@Service
public class ComptePatientService {

    @Autowired
    ComptePatientRepository comptePatientRepository ;

    // fiind  comptePatient By Id
    public ComptePatient getComptePatient(Long id){
        return comptePatientRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Compte Patient " + id + " not found"));

    }

    // fiind  All Membres Famille
    public Set<Patient> getAllMembresFamille(Long id){
        ComptePatient comptePatient = getComptePatient(id);
        return comptePatient.getMembresFamille();
    }

    // Creation Compte Patient
    public ComptePatient creer(ComptePatient comptePatient){
       comptePatientRepository.findByEmail(comptePatient.getEmail()).ifPresent(cp -> {
            throw new RuntimeException("Compte patient : " +comptePatient.getEmail()+" est deja exist");
        });
    return comptePatientRepository.save(comptePatient);
    }

    // Update Compte Patient
    public ComptePatient updateCompte( Long idCompte, ComptePatient comptePatientRequest){
        ComptePatient comptePatient=getComptePatient(idCompte);
        comptePatientRepository.findByEmail(comptePatientRequest.getEmail()).ifPresent(cp -> {
            throw new RuntimeException("Compte patient : " +comptePatient.getEmail()+" est deja exist");
        });
        comptePatient.setEmail(comptePatientRequest.getEmail());
        ComptePatient updateComptePatient=comptePatientRepository.save(comptePatient);
        return updateComptePatient;
    }

    public ComptePatient modifierMotDePasse(Long id, MotDePasseInfo motDePasseInfo){
        ComptePatient comptePatient=getComptePatient(id);
        if (motDePasseInfo.getAncienMotDePasse().equals(comptePatient.getMotDePasse())){
            comptePatient.setMotDePasse(motDePasseInfo.getNouveauMotDePasse());
            ComptePatient updateComptePatient=comptePatientRepository.save(comptePatient);
            return updateComptePatient;
        } else
        {
            throw new MotDePasseException("mot de passe invalid");
        }
    }

    public ResponseEntity<?> deleteCompte( Long idCompte){
        ComptePatient comptePatient=getComptePatient(idCompte);
        comptePatientRepository.delete(comptePatient);
        return ResponseEntity.ok().build();
    }
}
