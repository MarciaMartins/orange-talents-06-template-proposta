package com.zup.proposta.feignCliente.dto;

import com.zup.proposta.response.CarteiraResponse;

public class CarteiraResponseFeign {
	
	private String resultado;
	private String id;
	public CarteiraResponseFeign(String resultado, String id) {
		this.resultado = resultado;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public String getResultado() {
		return resultado;
	}

	public CarteiraResponse toModel() {
		return new CarteiraResponse(resultado, id);
	}

	

	

}
