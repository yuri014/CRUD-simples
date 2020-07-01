package com.trabalhofinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabalhofinal.entities.Hq;

@Repository
public interface HqRepository extends JpaRepository<Hq, Long>{

}
