package com.zup.proposta.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zup.proposta.controller.validation.AvisoViagemValidation;
import com.zup.proposta.controller.validation.RecuperaPropostaBiometria;
import com.zup.proposta.modelo.Aviso;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.request.ViagemRequest;

@RestController
@RequestMapping("/viagens")
public class ViagemController {

	@Autowired
	private AvisoViagemValidation avisoViagemValidation;

	@Autowired
	private RecuperaPropostaBiometria recuperaPropostaBiometria;

	private URI uri;
	private Aviso aviso;

	@PostMapping("/{id}")
	public ResponseEntity<?> recuperaStatusViagem(@PathVariable("id") String id,
			@RequestBody ViagemRequest viagemRequest, HttpServletResponse response, HttpServletRequest requeste) {
		Proposta proposta = recuperaPropostaBiometria.recuperaProposta(id);

		if (proposta != null) {
			String status = avisoViagemValidation.ValidaAvisoViagem(viagemRequest, id);
			if (!status.equals("CRIADO")) {
				return ResponseEntity.badRequest().build();
			}

			aviso = cria(requeste, proposta);
			uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId())
					.toUri();
			response.setHeader("Location", uri.toASCIIString());
			return ResponseEntity.created(uri).body(aviso);

		} else {
			return ResponseEntity.notFound().build();
		}

	}

	private Aviso cria(HttpServletRequest requeste, Proposta proposta) {
		String ip = requeste.getRemoteAddr();
		String userAgent = requeste.getHeader(HttpHeaders.USER_AGENT);

		Assert.notNull(ip, userAgent);
		Aviso aviso = avisoViagemValidation.RegistraAvisoViagem(proposta, ip, userAgent);
		return aviso;
	}

}
