package com.zup.proposta.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.zup.proposta.controller.validation.AtualizaBloqueioComponent;
import com.zup.proposta.controller.validation.RecuperaPropostaBiometria;
import com.zup.proposta.feignCliente.AccountsController;
import com.zup.proposta.feignCliente.dto.CartaoBloqueio;
import com.zup.proposta.modelo.Bloqueio;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.response.BloqueioResponse;
import com.zup.proposta.response.CartaoResponseNumero;

@RestController
@RequestMapping("/cartoes")
public class CartaoAcessoController {

	@Autowired
	private RecuperaPropostaBiometria recuperaPropostaBiometria;

	@Autowired
	private AtualizaBloqueioComponent atualizaBloqueio;
	
	@Autowired
	private AccountsController accountsController;

	@PostMapping("/{idCartao}")
	public ResponseEntity<?> cadastrar(@PathVariable String idCartao, @RequestBody @Valid CartaoBloqueio bloqueio,
			HttpServletResponse response, HttpServletRequest request) {

		Proposta proposta = recuperaPropostaBiometria.recuperaProposta(idCartao);
		if (proposta != null) {

			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(proposta.getId())
					.toUri();
			response.setHeader("Location", uri.toASCIIString());

			if (proposta.getStatus() == null) {

				Bloqueio bloqueioSalvo = atualizaBloqueio.insereDadosBloqueio(proposta, bloqueio, getId(request));
				BloqueioResponse bloqueioResponse = bloqueioSalvo.toResponse();
				if (bloqueioSalvo.getProposta().getStatus().equals("FALHA")) {
					return ResponseEntity.badRequest().body(bloqueioResponse);
				}
				return ResponseEntity.ok().body(bloqueioSalvo);
			}

			if (proposta.getStatus().equals("BLOQUEADO")) {
				return ResponseEntity.unprocessableEntity().body(proposta);
			}
			if (proposta.getStatus().equals("FALHA")) {
				return ResponseEntity.accepted().body(proposta);
			}

		}

		return ResponseEntity.notFound().build();

	}

	private String getId(HttpServletRequest request) {

		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null) {
			ipAddress = request.getHeader("X_FORWARDED_FOR");
			if (ipAddress == null) {
				ipAddress = request.getRemoteAddr();
			}
		}

		return ipAddress;
	}
	
	@GetMapping("/{idProposta}")
	public CartaoResponseNumero recuperaNumeroCartao(@PathVariable String idProposta) {
		CartaoResponseNumero numeroCartao = accountsController.getNumeroCartao(idProposta);
		return numeroCartao;
	}

}
