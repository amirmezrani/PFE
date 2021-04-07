package tn.isimg.pfe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.isimg.pfe.dto.DateRendezVous;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.model.Patient;
import tn.isimg.pfe.model.RendezVous;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.repository.DisponibiliteRepository;
import tn.isimg.pfe.repository.PatientRepository;
import tn.isimg.pfe.repository.RendezVousRepository;
;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RendezVousService {

    @Autowired
    RendezVousRepository rendezVousRepository;

    @Autowired
    DisponibiliteRepository disponibiliteRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ComptePatientRepository comptePatientRepository;

    // Get  Rendez-Vous By Id
    public RendezVous findById(Long id){
        return rendezVousRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("id Rendez-Vous " + id + " not found"));
    }


    // Get All Rendez-Vous d'un patient
    public List<RendezVous> findAllByPatient(Long id){
        return rendezVousRepository.findByPatientId(id);
    }

    public RendezVous creer(RendezVous rendezVous){

        rendezVousRepository.findByDisponibiliteId(rendezVous.getDisponibilite().getId())
                .ifPresent(rd -> {
                   throw new RuntimeException("disponibilite id : " +rendezVous.getDisponibilite().getId()+" est deja pris");
               });
        Disponibilite disponibilite = disponibiliteRepository.findById(rendezVous.getDisponibilite().getId()).orElseThrow(() -> new ResourceNotFoundException("id disponibilite " + rendezVous.getDisponibilite().getId() + "not found"));
        rendezVous.setDisponibilite(disponibilite);
        Patient patient = patientRepository.findById(rendezVous.getPatient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("id patient " + rendezVous.getPatient().getId() + "not found"));
        rendezVous.setPatient(patient);
        disponibilite.setRdv(true);
        return rendezVousRepository.save(rendezVous);
    }


    public RendezVous update(Long id,RendezVous rendezVousRequest){

        return rendezVousRepository.findById(id).map(rendezVous -> {
            rendezVous.getDisponibilite().setRdv(false);
            rendezVous.setDisponibilite(rendezVousRequest.getDisponibilite());
            rendezVous.getDisponibilite().setRdv(true);
            return rendezVousRepository.save(rendezVous);
        }).orElseThrow(() -> new ResourceNotFoundException("id Rendez-Vous " + id + "not found"));

    }


    public ResponseEntity<?> deleteRd(Long id) {
        return rendezVousRepository.findById(id).map(rendezVous -> {
            rendezVousRepository.delete(rendezVous);
            rendezVous.getDisponibilite().setRdv(false);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Rendez-Vous not found with id " +
                id));
    }

    public List<RendezVous> findAllByComptePatient(Long idComptePatient) {
        ComptePatient comptePatient = comptePatientRepository.findById(idComptePatient).
                orElseThrow(() -> new ResourceNotFoundException("id Compte Patient " + idComptePatient + " not found"));
        comptePatient.getPatientPrincipal().getId();
        List<Long> allIdPatients = comptePatient.getMembresFamille().stream().map(patient -> patient.getId()).collect(Collectors.toList());
        allIdPatients.add(comptePatient.getPatientPrincipal().getId());
        return rendezVousRepository.findByPatientIdIn(allIdPatients);
    }
}
