package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isimg.pfe.dto.DateRendezVous;
import tn.isimg.pfe.model.RendezVous;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RendezVousRepository extends JpaRepository<RendezVous,Long> {
    Optional<RendezVous> findByDisponibiliteId(Long disponibiliteId);
    List<RendezVous> findByPatientId(Long patientId);
    List<RendezVous> findByPatientIdIn(List<Long> allIdPatients);
}
