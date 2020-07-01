package com.trabalhofinal.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Musica {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY, generator="idmusica")
	@Getter
	private Long id;
	
	@Getter
	@Setter
	private String nomeMusica;
	
	@Getter
	@Setter
	private String artista;
	
	@Getter
	@Setter
	private int anoLancamento;
	
}
