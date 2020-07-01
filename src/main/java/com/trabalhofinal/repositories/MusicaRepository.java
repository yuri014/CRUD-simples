package com.trabalhofinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trabalhofinal.entities.Musica;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
