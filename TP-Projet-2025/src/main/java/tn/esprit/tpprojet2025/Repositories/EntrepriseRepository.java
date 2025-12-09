package tn.esprit.tpprojet2025.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpprojet2025.Entities.Entreprise;

public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
}
