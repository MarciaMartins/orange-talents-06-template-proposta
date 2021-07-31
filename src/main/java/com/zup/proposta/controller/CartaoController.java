package com.zup.proposta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zup.proposta.feignCliente.AccountsController;
import com.zup.proposta.feignCliente.CartoesClient;
import com.zup.proposta.feignCliente.dto.CartaoBloqueio;
import com.zup.proposta.feignCliente.dto.CartaoBloqueioResponse;
import com.zup.proposta.feignCliente.dto.CartaoResponse;
import com.zup.proposta.feignCliente.dto.CartaoSolicitacao;
import com.zup.proposta.response.CartaoResponseNumero;

import feign.FeignException;

@RestController
public class CartaoController {

	@Autowired
	private CartoesClient cartaoCliente;
	
	@Autowired
	private AccountsController accountsController;

	private String statusDevolutiva;

	@PostMapping
	public String validaCartao(@RequestBody CartaoSolicitacao request) {
		try {
			CartaoResponse responseCartao = cartaoCliente.getSolicitacao(request);
			statusDevolutiva = responseCartao.getResultadoSolicitacao();
		} catch (FeignException e) {
			statusDevolutiva = "COM_RESTRICAO";
		}
		return statusDevolutiva;
	}

	@GetMapping("/{idProposta}")
	public CartaoResponseNumero recuperaNumeroCartao(@PathVariable String idProposta) {
		CartaoResponseNumero numeroCartao = accountsController.getNumeroCartao(idProposta);
		return numeroCartao;
	}

	@PostMapping("/{idCartao}")
	public String recuperaStatusBloqueio(@PathVariable String idCartao, @RequestBody CartaoBloqueio bloqueio) {
		try {
			CartaoBloqueioResponse bloqueioResponse = accountsController.postBloqueioCartao(idCartao, bloqueio);
			statusDevolutiva = bloqueioResponse.getResultado();
		} catch (FeignException e) {
			statusDevolutiva = "FALHA";
		}
		return statusDevolutiva;

	}

}
