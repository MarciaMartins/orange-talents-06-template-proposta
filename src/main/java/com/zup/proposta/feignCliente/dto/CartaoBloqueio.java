package com.zup.proposta.feignCliente.dto;

import javax.validation.constraints.NotBlank;

public class CartaoBloqueio {
	
	@NotBlank
	private String sistemaResponsavel;

	
	public String getSistemaResponsavel() {
		return sistemaResponsavel;
	}

	public CartaoBloqueio(String sistemaResponsavel) {
		this.sistemaResponsavel = sistemaResponsavel;
	}
	
	public CartaoBloqueio() {
		// TODO Auto-generated constructor stub
	}
	
	

}
