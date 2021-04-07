package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Medecin;

import java.util.List;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
    List<Medecin> findBySpecialiteId(Long id);
    Optional<Medecin> findByEmail(String email);
    List<Medecin> findByVilleId(Long id);
    List<Medecin> findBySpecialiteIdAndVilleId(Long idSpecialite,Long idVille);

}
