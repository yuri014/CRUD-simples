package com.trabalhofinal.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.trabalhofinal.entities.Hq;
import com.trabalhofinal.repositories.HqRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@Controller
@Api(description="Operações do serviço relacionadas a entidade Hq do banco de dados.")
@RequestMapping("/hqs")
@CrossOrigin(origins="*")
public class HqController {

	@Autowired
	private HqRepository hqRepository;
	
	@ApiOperation(value="Lista de HQs cadastradas.")
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Hq>> listHqs() {
		return new ResponseEntity<List<Hq>>(hqRepository.findAll(), new HttpHeaders(), HttpStatus.OK);
	}
	
    @ApiOperation(value="Pesquisa uma pessoa pelo seu id")
    @GetMapping(path="/{id}", produces="application/json")
    public ResponseEntity<Hq> getHq(@PathVariable("id") Long id) throws NotFoundException {
        if (hqRepository.findById(id).isPresent() == false) {
            throw new NotFoundException("Não foi encontrada uma HQ com o id informado!");
        }else {
        	return new ResponseEntity<Hq>(hqRepository.findById(id).get(), new HttpHeaders(), HttpStatus.OK);
        }
    }
	
	@ApiOperation(value="Cadastra uma HQ.")
	@PostMapping(produces = "application/json")
	public ResponseEntity<Hq> addHq(@RequestBody Hq hq) {
		return new ResponseEntity<Hq>(hqRepository.save(hq), new HttpHeaders(), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Deleta uma HQ pelo seu id.")
	@DeleteMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Hq> deleteHq(@PathVariable("id") Long id) {
		hqRepository.deleteById(id);
		return new ResponseEntity<Hq>(HttpStatus.OK);
	}
	
	@ApiOperation(value="Altera uma HQ pelo seu id.")
	@PutMapping(produces = "application/json")
	public ResponseEntity<Hq> updateHq(@RequestBody Hq hq) throws NotFoundException {
		Calendar calendario = GregorianCalendar.getInstance();
		if (hq.getId() == null) {
			throw new NotFoundException("Informe o id da hq a ser alterada!");
		} else if (hqRepository.existsById(hq.getId()) == false) {
			throw new NotFoundException("Não existe hq cadastrada com este id!");
		} else if (hq.getAutor() == "" || hq.getAutor() == null) {
			throw new NotFoundException("Informe o nome do autor a ser alterado!");
		} else if (hq.getNomeHQ() == "" || hq.getNomeHQ() == null) {
			throw new NotFoundException("Informe o nome da hq a ser alterada!");
		}else if (hq.getDataPublicacao() > (calendario.get(Calendar.YEAR))) {
			throw new NotFoundException("Informe um ano válido da hq a ser alterada!");
		} else {
			return new ResponseEntity<Hq>(hqRepository.save(hq), new HttpHeaders(), HttpStatus.OK);
		}
	}
	
}
