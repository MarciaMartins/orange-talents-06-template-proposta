package com.zup.proposta.request;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.sun.istack.NotNull;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.validation.CpfCnpj;
import com.zup.proposta.validation.ValorUnico;

public class PropostaRequest {
	@NotBlank 
	@CpfCnpj
	@ValorUnico(domainClass = Proposta.class, fieldName = "documento", message = "Proposta existente para este documento")
	private String documento;
	@NotBlank @Email 
	@ValorUnico(domainClass = Proposta.class, fieldName = "email", message = "Email deve ser único.")
	private String email;
	@NotBlank
    private String nome; 
	@NotBlank
    private String endereco;
	@NotNull
	@Positive
    private BigDecimal salario;
	
	public String getDocumento() {
		return documento;
	}
	public String getEmail() {
		return email;
	}
	public String getNome() {
		return nome;
	}
	public String getEndereco() {
		return endereco;
	}
	public BigDecimal getSalario() {
		return salario;
	}
	
	public PropostaRequest(String documento, String email, String nome, String endereco, 
			BigDecimal salario) {
		this.documento = documento;
		this.email = email;
		this.nome = nome;
		this.endereco = endereco;
		this.salario = salario;
	}
	
	public Proposta toModel() {
		return new Proposta(documento, email, nome, endereco, salario);
	}
    
    
}
