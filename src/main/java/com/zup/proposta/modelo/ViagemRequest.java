package com.zup.proposta.modelo;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;

import org.springframework.cloud.openfeign.FeignClient;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import com.zup.proposta.feignCliente.dto.ViagemFeign;

public class ViagemRequest {
	@NotBlank

	private String destino;

	@NotNull
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String validoAte;
	@NotBlank
	public String getDestino() {
		return destino;
	}

	public String getValidoAte() {
		return validoAte;
	}

	@JsonCreator
	public ViagemFeign convertFeig() throws ParseException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.parse(validoAte, formatter);
		return new ViagemFeign(destino, localDate);
	}

	//
	// this.destination = destination;

}
