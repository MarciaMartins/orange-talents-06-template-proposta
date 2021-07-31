package com.zup.proposta.feignCliente.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraResquestFeign {
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Enumerated(EnumType.STRING)
	private Enum carteira;
	
	public CarteiraResquestFeign() {
		// TODO Auto-generated constructor stub
	}

	public CarteiraResquestFeign(String email, Enum carteira) {

		this.email = email;
		this.carteira = carteira;
	}

	public Enum getCarteira() {
		return carteira;
	}

	public String getEmail() {
		return email;
	}
}
