package com.usura.sga.repository;


import com.usura.sga.entity.EstudianteEntity;
import com.usura.sga.entity.MatriculaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMatriculaRepository extends JpaRepository<MatriculaEntity, Integer> {
    MatriculaEntity findByIdMatricula(Integer idMatricula);

}
