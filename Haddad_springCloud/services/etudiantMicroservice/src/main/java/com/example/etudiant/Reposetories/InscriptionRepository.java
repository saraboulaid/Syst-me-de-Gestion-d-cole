package com.example.etudiant.Reposetories;

import com.example.etudiant.Entities.Inscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InscriptionRepository extends JpaRepository<Inscription, Integer> {
    Optional<Inscription> findByEtudiantMatriculeAndModuleId(String matricule, String moduleId);
    List<Inscription> findByEtudiantMatricule(String matricule);
    @Query("SELECT COUNT(i) FROM Inscription i")
    long count();
    @Query(value = "SELECT i.module_id, COUNT(i.id) as total FROM inscription i GROUP BY i.module_id ORDER BY total DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopModules();
    @Query(value = "SELECT i.etudiant_matricule, COUNT(i.id) as total FROM inscription i GROUP BY i.etudiant_matricule ORDER BY total DESC LIMIT 5", nativeQuery = true)
    List<Object[]> findTopEtudiants();

}