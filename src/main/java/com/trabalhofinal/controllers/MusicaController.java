package com.trabalhofinal.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trabalhofinal.entities.Musica;
import com.trabalhofinal.repositories.MusicaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@Controller
@Api(description="Operações do serviço relacionadas a entidade Música do banco de dados.")
@RequestMapping("/musicas")
public class MusicaController {

	@Autowired
	private MusicaRepository musicaRepository;

	@ApiOperation(value="Lista de músicas cadastradas.")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Musica>> listMusicas() {
		return new ResponseEntity<List<Musica>>(musicaRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
	}
	
    @ApiOperation(value="Pesquisa uma HQ pelo seu id")
    @GetMapping(path="/{id}", produces="application/json")
    public ResponseEntity<Musica> getPessoa(@PathVariable Long id) throws NotFoundException {
    	if (musicaRepository.findById(id).isPresent() == false) {
            throw new NotFoundException("Não foi encontrada uma música com o id informado!");
    	}else {
    		return new ResponseEntity<Musica>(musicaRepository.findById(id).get(), new HttpHeaders(), HttpStatus.OK);
    	}
    }

	@ApiOperation(value="Cadastra uma música.")
	@PostMapping(produces = "application/json")
	public ResponseEntity<Musica> addMusica(@RequestBody Musica musica) {
		return new ResponseEntity<Musica>(musicaRepository.save(musica), new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiOperation(value="Deleta uma música pelo seu id.")
	@DeleteMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Musica> deleteMusica(@PathVariable("id") Long id) {
		musicaRepository.deleteById(id);
		return new ResponseEntity<Musica>(HttpStatus.OK);
	}

	@ApiOperation(value="Altera uma música pelo seu id.")
	@PutMapping(produces = "application/json")
	public ResponseEntity<Musica> updateMusica(@RequestBody Musica musica) throws NotFoundException {
		Calendar calendario = GregorianCalendar.getInstance();
		if (musica.getId() == null) {
			throw new NotFoundException("Informe o id da música a ser alterada!");
		} else if (musicaRepository.existsById(musica.getId()) == false) {
			throw new NotFoundException("Não existe música cadastrada com este id!");
		} else if (musica.getArtista() == "" || musica.getArtista() == null) {
			throw new NotFoundException("Informe o nome do artista a ser alterado!");
		} else if (musica.getNomeMusica() == "" || musica.getNomeMusica() == null) {
			throw new NotFoundException("Informe o nome da música a ser alterada!");
		}else if (musica.getAnoLancamento() > (calendario.get(Calendar.YEAR))) {
			throw new NotFoundException("Informe um ano válido da música a ser alterada!");
		} else {
			return new ResponseEntity<Musica>(musicaRepository.save(musica), new HttpHeaders(), HttpStatus.OK);
		}
	}

}
