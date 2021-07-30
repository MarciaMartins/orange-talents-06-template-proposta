package com.zup.proposta.controller.validation;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zup.proposta.controller.CartaoController;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.repository.PropostaRepository;
import com.zup.proposta.response.CartaoResponseNumero;

@Component
public class AtualizaProposta {

	@Autowired
	private CartaoController cartaoController;

	@Autowired
	private PropostaRepository propostaRepository;

	//@Scheduled(fixedDelay = 50000)
	private void AtualizaPropostaDadosCartao() {
		List<Proposta> listaPropostasElegiveis = propostaRepository.findPropostaByElegivelAndCartao("ELEGIVEL", null);
		if (!listaPropostasElegiveis.isEmpty()) {
			for (Proposta proposta : listaPropostasElegiveis) {
				if (proposta.getCartao() == null) {
					CartaoResponseNumero cartaoresponse = cartaoController
							.recuperaNumeroCartao(proposta.getId().toString());
					String stringCartao = cartaoresponse.getId().toString();
					atualizaProposta(stringCartao, proposta);
					listaPropostasElegiveis.remove(proposta);
				}
			}
		}

	}

	public void AtualizaPropostaStatus(String status, Proposta proposta) {
		Proposta atualProposta = proposta;
		proposta.setStatus(status);
		BeanUtils.copyProperties(proposta, atualProposta);
		propostaRepository.save(atualProposta);
	}

	private void atualizaProposta(String cartaoresponse, Proposta proposta) {
		Proposta atualProposta = proposta;
		atualProposta.setCartao(cartaoresponse);
		BeanUtils.copyProperties(proposta, atualProposta);
		propostaRepository.save(atualProposta);
	}

}
