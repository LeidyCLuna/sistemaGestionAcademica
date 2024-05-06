package com.usura.sga.repository;

import com.usura.sga.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICursoRepository extends JpaRepository <CursoEntity, Integer> {
}
