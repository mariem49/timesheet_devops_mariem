package tn.esprit.tpprojet2025.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpprojet2025.Entities.Projet;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    
}
