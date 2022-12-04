package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.dto.MotDePasseInfo;
import tn.isimg.pfe.exception.ExistException;
import tn.isimg.pfe.exception.MotDePasseException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.repository.ComptePatientRepository;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class ComptePatientService {

    @Autowired
    ComptePatientRepository comptePatientRepository ;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MailSenderService mailSenderService;

    // fiind all comptePatient
     public List<ComptePatient> getAllComptePatient()
    {
        return comptePatientRepository.findAll();
    }

    // fiind  comptePatient By Id
    public ComptePatient getComptePatientById(Long id){
        return comptePatientRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("id Compte Patient " + id + " not found"));

    }

//    // Creation Compte Patient
//    public ComptePatient creerComptePatient(ComptePatient comptePatient){
//        comptePatientRepository.findByEmail(comptePatient.getEmail()).ifPresent(cp -> {
//            throw new ExistException("Compte patient : " +comptePatient.getEmail()+" est deja exist");
//        });
//        comptePatient.setMotDePasse(passwordEncoder.encode(comptePatient.getMotDePasse()));
//        return comptePatientRepository.save(comptePatient);
//    }

    // find Compte Patient by email
    public ComptePatient getCompteByEmail(String email){
       ComptePatient comptePatient=comptePatientRepository.findByEmail(email).
                       orElseThrow(()->new ExistException("Email Compte Patient "+ email+ " not found"));
    return comptePatient;
     }

    // Creation Compte Patient
    public ComptePatient creerComptePatient(ComptePatient comptePatient){
       comptePatientRepository.findByEmail(comptePatient.getEmail()).ifPresent(cp -> {
            throw new ExistException("Compte patient : " +comptePatient.getEmail()+" est deja exist");
        });
       comptePatient.setMotDePasse(passwordEncoder.encode(comptePatient.getMotDePasse()));
    return comptePatientRepository.save(comptePatient);
    }

    // Update Compte Patient
    public ComptePatient updateCompte( Long idComptePatient, ComptePatient comptePatientRequest){
        ComptePatient comptePatient= getComptePatientById(idComptePatient);
//        comptePatientRepository.findByEmail(comptePatientRequest.getEmail()).ifPresent(cp -> {
//            throw new ExistException("Compte patient : " +comptePatient.getEmail()+" est deja exist");
//        });
        comptePatient.setEmail(comptePatientRequest.getEmail());
        comptePatient.setPatientPrincipal(comptePatientRequest.getPatientPrincipal());
        ComptePatient updateComptePatient=comptePatientRepository.save(comptePatient);
        return updateComptePatient;
    }

    public ComptePatient modifierMotDePasseComptePatient(Long id, MotDePasseInfo motDePasseInfo){
        ComptePatient comptePatient= getComptePatientById(id);
        String mdpAncien=motDePasseInfo.getAncienMotDePasse();

        if (passwordEncoder.matches(mdpAncien,comptePatient.getMotDePasse())){
            comptePatient.setMotDePasse(motDePasseInfo.getNouveauMotDePasse());
            ComptePatient updateComptePatient=comptePatientRepository.save(comptePatient);

            try {
                this.mailSenderService.send(comptePatient.getEmail(),
                        "Votre Mot De Passe est changé  ",
                        "merci de consulter votre compte");

            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return updateComptePatient;

        } else {
            throw new MotDePasseException("mot de passe invalid");
        }
    }

    public ResponseEntity<?> deleteCompte( Long idComptePatient){
        ComptePatient comptePatient= getComptePatientById(idComptePatient);
        String email=comptePatient.getEmail();
        comptePatientRepository.delete(comptePatient);
        try {
            this.mailSenderService.send(email,
                    "Votre compte est supprimé  ",
                    "merci de");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
}
