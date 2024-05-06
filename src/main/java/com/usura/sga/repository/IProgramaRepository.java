package com.usura.sga.repository;


import com.usura.sga.entity.ProgramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProgramaRepository extends JpaRepository <ProgramaEntity, Integer> {
}
