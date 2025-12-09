package tn.esprit.tpprojet2025.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpprojet2025.Entities.ProjetDetail;

@Repository
public interface ProjetDetailRepository extends JpaRepository <ProjetDetail, Long> {
}
