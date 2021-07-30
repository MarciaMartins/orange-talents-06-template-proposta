package com.zup.proposta.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.zup.proposta.modelo.Carteira;
import com.zup.proposta.modelo.Proposta;

public class CarteiraResponse {
	
	private String resultado;
	@JsonAlias(value = "id")
	private String idCarteira;
	
	public CarteiraResponse() {
		// TODO Auto-generated constructor stub
	}
	
	public CarteiraResponse(String resultado, String idCarteira) {
		this.resultado = resultado;
		this.idCarteira = idCarteira;
	}
	
	public String getId() {
		return idCarteira;
	}
	
	public String getResultado() {
		return resultado;
	}

	public Carteira toModel(CarteiraResponse responseCarteira, Proposta proposta) {
		return new Carteira(responseCarteira.getResultado(), responseCarteira.getId(), proposta);
	}
	
	

}
