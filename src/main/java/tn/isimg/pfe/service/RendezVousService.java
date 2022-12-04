package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.*;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.repository.DisponibiliteRepository;
import tn.isimg.pfe.repository.RendezVousRepository;
;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    @Autowired
    RendezVousRepository rendezVousRepository;

    @Autowired
    RendezVousService rendezVousService;

    @Autowired
    DisponibiliteRepository disponibiliteRepository;

    @Autowired
    PatientService patientService ;

    @Autowired
    MedecinService medecinService;

    @Autowired
    DisponibiliteService disponibiliteService;

    @Autowired
    ComptePatientRepository comptePatientRepository;

    @Autowired
    ComptePatientService comptePatientService;

    @Autowired
    MailSenderService mailSenderService;

    // Get  Rendez-Vous By Id
    public RendezVous findById(Long id){
        return rendezVousRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Rendez-Vous " + id + " not found"));
    }


    // Get All Rendez-Vous d'un patient
    public List<RendezVous> findAllRdvByIdPatient(Long id){
        return rendezVousRepository.findByPatientId(id);
    }


    public List<RendezVous> findAllRdvByIdComptePatient(Long idComptePatient) {

        ComptePatient comptePatient = comptePatientService.getComptePatientById(idComptePatient);
        comptePatient.getPatientPrincipal().getId();
        List<Long> allIdPatients =patientService.getAllMembresFamilleByComptePatient(idComptePatient)
                .stream().map(patient -> patient.getId()).collect(Collectors.toList());
        allIdPatients.add(comptePatient.getPatientPrincipal().getId());
        return rendezVousRepository.findByPatientIdIn(allIdPatients);

    }

    // Get All Rendez-Vous d'un medecin
    public List<RendezVous> findAllRdvByIdMedecin(Long idMedecin) {

        Medecin medecin=medecinService.getMedecinById(idMedecin);
        List<Long> allIdDisponibilites=disponibiliteService.findAllDisponibiliteByIdMedecin(idMedecin)
                .stream().map(disponibilite -> disponibilite.getId()).collect(Collectors.toList());

        return rendezVousRepository.findByDisponibiliteIdIn(allIdDisponibilites);
    }

    // Get All Rendez-Vous d'un medecin By Date
    public List<RendezVous> findAllRdvByIdMedecinByDate(Long idMedecin,
                                                        LocalDateTime dateDebut, LocalDateTime dateFin) {

        Medecin medecin=medecinService.getMedecinById(idMedecin);
        List<Long> allIdDisponibilites=disponibiliteService.findByIdMedecinAndDateWithRdv(idMedecin,dateDebut,dateFin)
                .stream().map(disponibilite -> disponibilite.getId()).collect(Collectors.toList());

        List<RendezVous> rendezVous=rendezVousRepository.findByDisponibiliteIdIn(allIdDisponibilites);

        return rendezVous;
    }

    // Get  Rendez-Vous By Id
    public RendezVous findRDVByIdDisponibilite(Long idDisponibilite){
      return rendezVousRepository.findByDisponibiliteId(idDisponibilite)
               .orElseThrow(() -> new ResourceNotFoundException("Rendez-Vous  not found"));
    }


    public RendezVous creerRDV(RendezVous rendezVous){

        rendezVousRepository.findByDisponibiliteId(rendezVous.getDisponibilite().getId())
                .ifPresent(rd -> {
                   throw new RuntimeException("disponibilite id : " +rendezVous.getDisponibilite().getId()+" est deja pris");
               });
        Disponibilite disponibilite = disponibiliteService.findDisponibiliteById(rendezVous.getDisponibilite().getId());
        rendezVous.setDisponibilite(disponibilite);
        Patient patient = patientService.getPatientById(rendezVous.getPatient().getId());
        rendezVous.setPatient(patient);
        disponibilite.setRdv(true);
        return rendezVousRepository.save(rendezVous);
    }


    public RendezVous updateRDV(Long id, RendezVous rendezVousRequest){

        RendezVous rendezVous=findById(id);
        rendezVous.getDisponibilite().setRdv(false);
        rendezVous.setDisponibilite(rendezVousRequest.getDisponibilite());
        rendezVous.getDisponibilite().setRdv(true);
        Patient patient=patientService.getPatientById(rendezVous.getPatient().getId());

        try {
            this.mailSenderService.send(patient.getEmail(),
                    "Votre Rendez-vous est modifié  ",
                    "merci  de consulter votre compte");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return rendezVousRepository.save(rendezVous);
    }


    public ResponseEntity<?> deleteRDV(Long idRDV) {
        RendezVous rendezVous=findById(idRDV);
        rendezVous.getDisponibilite().setRdv(false);
        Patient patient=patientService.getPatientById(rendezVous.getPatient().getId());
        rendezVousRepository.delete(rendezVous);
        try {
            this.mailSenderService.send(patient.getEmail(),
                    "Votre Rendez-vous est annulé  ",
                    "merci  de consulter votre compte");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteRDVByIdMedecinDate(Long idMedecin, LocalDateTime dateDebut, LocalDateTime dateFin) {
        Medecin medecin=medecinService.getMedecinById(idMedecin);
        List<RendezVous> rendezVousList = findAllRdvByIdMedecinByDate(idMedecin, dateDebut, dateFin);


        for (RendezVous rendezVous : rendezVousList) {
            rendezVous.getDisponibilite().setRdv(false);
            Patient patient=patientService.getPatientById(rendezVous.getPatient().getId());
            rendezVousRepository.delete(rendezVous);
            try {
                this.mailSenderService.send(patient.getEmail(),
                        "Votre Rendez-vous est annulé  ",
                        "merci  de consulter votre compte");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok().build();
    }


}
