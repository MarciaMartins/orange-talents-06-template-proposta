package com.zup.proposta.feignCliente.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotBlank;

public class ViagemRequestFeign {
	@NotBlank
	private String destino;
	private String validoAte;

	
	public String getDestino() {
		return destino;
	}

	public String getValidoAte() {
		return validoAte;
	}

	public ViagemRequestFeign( String destino, Date validoAte) {
		
		this.destino = destino;
		this.validoAte = converteString(validoAte);
	}
	
	private String converteString(Date validoAte) {
		 //Date dataAtual = new Date();
	     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     String dataFormatada = dateFormat.format(validoAte);
	     return dataFormatada;
	}
	
	

}
