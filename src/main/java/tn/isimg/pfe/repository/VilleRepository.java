package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Specialite;
import tn.isimg.pfe.model.Ville;

import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville,Long> {

    Optional<Ville> findByVille(String ville);
}
