package com.zup.proposta.feignCliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zup.proposta.feignCliente.dto.CartaoResponse;
import com.zup.proposta.feignCliente.dto.CartaoSolicitacao;

@FeignClient(name = "solicitacao", url = "http://localhost:9999/")
public interface CartoesClient {

	@RequestMapping(method = RequestMethod.POST, value = "api/solicitacao")
	CartaoResponse getSolicitacao(CartaoSolicitacao cartao);

}
