package com.zup.proposta.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zup.proposta.controller.validation.RecuperaPropostaBiometria;
import com.zup.proposta.controller.validation.ValidaCarteira;
import com.zup.proposta.feignCliente.AccountsController;
import com.zup.proposta.feignCliente.dto.CarteiraResponseFeign;
import com.zup.proposta.feignCliente.dto.CarteiraResquestFeign;
import com.zup.proposta.modelo.Carteira;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.repository.CarteiraRepository;
import com.zup.proposta.response.CarteiraResponse;

import feign.FeignException;


@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

	@Autowired
	private RecuperaPropostaBiometria recuperaPropostaBiometria;

	@Autowired
	private ValidaCarteira validaCarteira;

	@Autowired
	private AccountsController accountsController;
	
	@Autowired
	private CarteiraRepository carteiraRepository;
	
	private URI uri;
	private String statusDevolutiva;
	private CarteiraResponse responseCarteira = new CarteiraResponse();

	@PostMapping("/{idCartao}")
	public ResponseEntity<Carteira> cadastrar(@PathVariable String idCartao,
			@Valid @RequestBody CarteiraResquestFeign carteiraFeign, HttpServletResponse response,
			HttpServletRequest requeste) {
		Proposta proposta = recuperaPropostaBiometria.recuperaProposta(idCartao);

		if (proposta != null) {
			// CarteiraResponseFeign responseFeign =
			// accountsController.postCarteira(idCartao, carteiraFeign);
			statusDevolutiva = validaCarteira(carteiraFeign, idCartao);
			System.err.println(statusDevolutiva);
			if (statusDevolutiva.equals("FALHA")) {
				return ResponseEntity.badRequest().build();
			}
			Carteira carteiraModel = responseCarteira.toModel(responseCarteira, proposta);
			Carteira carteiraSalva = carteiraRepository.save(carteiraModel);
			
			uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId())
					.toUri();
			response.setHeader("Location", uri.toASCIIString());
			return ResponseEntity.created(uri).body(carteiraSalva);
		} else
			return ResponseEntity.notFound().build();

	}

	@PostMapping
	public String validaCarteira(CarteiraResquestFeign carRequest, String idCartao) {
		try {
			CarteiraResponseFeign responseFeign = accountsController.postCarteira(idCartao, carRequest);
			responseCarteira = responseFeign.toModel();
			statusDevolutiva = responseFeign.getResultado();
			
		} catch (FeignException e) {
			statusDevolutiva = "FALHA";
		}
		return statusDevolutiva;
	}
}
