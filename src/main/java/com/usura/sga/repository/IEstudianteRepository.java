package com.usura.sga.repository;

import com.usura.sga.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEstudianteRepository extends JpaRepository <EstudianteEntity, Integer> {
}
