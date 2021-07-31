package com.zup.proposta.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zup.proposta.feignCliente.AccountsController;
import com.zup.proposta.feignCliente.dto.CarteiraResponseFeign;
import com.zup.proposta.feignCliente.dto.CarteiraResquestFeign;

import feign.FeignException;

@Component
public class ValidaCarteira {

	@Autowired
	private AccountsController accountsController;

	public String ValidaCadastssdroCarteira(CarteiraResquestFeign carRequest, String idCartao) {
		String statusDevolutiva;
		try {
			CarteiraResponseFeign responseFeign = accountsController.postCarteira(idCartao, carRequest);
			statusDevolutiva = responseFeign.getResultado();
		} catch (FeignException e) {
			statusDevolutiva = "FALHA";
		}
		return statusDevolutiva;
		
	}
}
