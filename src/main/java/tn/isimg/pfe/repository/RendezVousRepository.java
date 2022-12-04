package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Disponibilite;
import tn.isimg.pfe.model.RendezVous;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {
    Optional<RendezVous> findByDisponibiliteId(Long disponibiliteId);
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByDisponibiliteIdIn(List<Long> allIdDisponibilites);
    List<RendezVous> findByPatientIdIn(List<Long> allIdPatients);
}
