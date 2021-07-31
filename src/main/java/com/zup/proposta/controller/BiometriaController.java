package com.zup.proposta.controller;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zup.proposta.controller.validation.CadastraBiometriaCartao;
import com.zup.proposta.controller.validation.RecuperaPropostaBiometria;
import com.zup.proposta.modelo.Biometria;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.request.BiometriaRequest;

@RestController
@RequestMapping("/biometrias")
public class BiometriaController {

	// @Autowired
	// private BiometriaRepository biometriaRepository;

	@Autowired
	private CadastraBiometriaCartao cadastraBiometriaCartao;

	@Autowired
	private RecuperaPropostaBiometria recuperaPropostaBiometria;

	// @GetMapping
	// public List<Biometria> listarTodos() {
	// return biometriaRepository.findAll();
	// }

	@PostMapping
	public ResponseEntity<Biometria> cadastrar(@Valid @RequestBody BiometriaRequest request,
			HttpServletResponse response) {

		Proposta proposta = recuperaPropostaBiometria.recuperaProposta(request.getId_cartao());
		Biometria biometria = cadastraBiometriaCartao.cadastraBiometria(request, proposta);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(biometria.getId())
				.toUri();
		return proposta != null ? ResponseEntity.created(uri).body(biometria) : ResponseEntity.notFound().build();

	}

}
