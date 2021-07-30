package com.zup.proposta.feignCliente.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraResquestFeign {
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String carteira;

	public CarteiraResquestFeign(String email, String carteira) {

		this.email = email;
		this.carteira = carteira;
	}

	public String getCarteira() {
		return carteira;
	}

	public String getEmail() {
		return email;
	}
}
