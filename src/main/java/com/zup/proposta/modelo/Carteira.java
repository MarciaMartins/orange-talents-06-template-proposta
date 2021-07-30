package com.zup.proposta.modelo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Carteira {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String resultado;
	
	private String idCarteira;
	@OneToOne
	@JoinColumn(name = "codigo_proposta")
	private Proposta proposta;
	private LocalDateTime criacao;

	public Carteira(String resultado, String idCarteira, Proposta proposta) {
		super();
		this.resultado = resultado;
		this.idCarteira = idCarteira;
		this.proposta = proposta;
		this.criacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getResultado() {
		return resultado;
	}

	public String getIdCarteira() {
		return idCarteira;
	}

	public Proposta getProposta() {
		return proposta;
	}

	public LocalDateTime getCriacao() {
		return criacao;
	}

}
