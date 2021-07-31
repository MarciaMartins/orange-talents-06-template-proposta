package com.zup.proposta.controller.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zup.proposta.controller.CartaoController;
import com.zup.proposta.feignCliente.dto.CartaoBloqueio;
import com.zup.proposta.modelo.Bloqueio;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.repository.BloqueioRepository;

@Component
public class AtualizaBloqueioComponent {
	@Autowired
	private CartaoController cartaoController;
	
	@Autowired
	private AtualizaPropostaComponent atualizaProposta;
	
	@Autowired
	private BloqueioRepository bloqueiRepository;
	
	public Bloqueio insereDadosBloqueio(Proposta proposta, CartaoBloqueio bloqueio, String ip) {

		String status = cartaoController.recuperaStatusBloqueio(proposta.getCartao(), bloqueio);
		atualizaProposta.AtualizaPropostaStatus(status, proposta);
		Bloqueio bloqueioModel = new Bloqueio(proposta, ip);
		bloqueiRepository.save(bloqueioModel);
		return bloqueioModel;
	}
	
	public List<Bloqueio> recuperaDadosBloqueio(Proposta proposta) {
		List<Bloqueio> bloqueioProposta = new ArrayList<>();
		List<Bloqueio> bloqueioRecuperado = bloqueiRepository.findAll();
		for (Bloqueio bloqueio : bloqueioRecuperado) {
			if(bloqueio.getProposta().equals(proposta)) {
				bloqueioProposta.add(bloqueio);
			}
		}
		if (bloqueioProposta.isEmpty()) return null;
		
		return bloqueioRecuperado;
	}
}
