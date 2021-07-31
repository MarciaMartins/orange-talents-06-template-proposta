package com.zup.proposta.feignCliente.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ViagemFeign {

	private String destino;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate validoAte;
	
	public ViagemFeign() {
		// TODO Auto-generated constructor stub
	}

	public ViagemFeign(String destino, LocalDate validoAte) {
		super();
		this.destino = destino;
		this.validoAte = validoAte;
	}

	public String getDestino() {
		return destino;
	}

	public LocalDate getValidoAte() {
		return validoAte;
	}

}
