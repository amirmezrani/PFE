package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.isimg.pfe.model.Disponibilite;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {
   List<Disponibilite> findByMedecinIdAndDateTimeBetween(Long medecinId, LocalDateTime dateDebut, LocalDateTime dateFin);

   Optional<Disponibilite> findByDateTime(LocalDateTime dateTime);

   List<Disponibilite> findByMedecinId(Long medecinId);

   Optional<Disponibilite> findByMedecinIdAndDateTime(Long medecinId,LocalDateTime dateTime);

}
