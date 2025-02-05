package com.zup.proposta.controller.validation;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.repository.PropostaRepository;
import com.zup.proposta.request.PropostaRequest;

@Component
public class CriaProposta {

	@Autowired
	private PropostaRepository propostaRepository;

	@Autowired
	private ValidaPropostaCliente validaPropostaCliente;

	private String status;

	public Proposta constroiProposta(PropostaRequest request) {
		Proposta propostaRequest = request.toModel();
		Proposta proposta = propostaRepository.save(propostaRequest);
		status = validaPropostaCliente.validaDevolutiva(proposta);
		Proposta atualizaProposta = atualizaProposta(proposta);
		return atualizaProposta;
	}

	private Proposta atualizaProposta(Proposta proposta) {
		Proposta atualizaProposta = propostaRepository.getById(proposta.getId());
		atualizaProposta.setElegivel(status);
		BeanUtils.copyProperties(proposta, atualizaProposta);
		atualizaProposta = propostaRepository.save(atualizaProposta);
		return atualizaProposta;
	}

}
