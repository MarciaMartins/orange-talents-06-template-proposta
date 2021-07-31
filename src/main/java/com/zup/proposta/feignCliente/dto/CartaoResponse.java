package com.zup.proposta.feignCliente.dto;

public class CartaoResponse {
	private String resultado;
	
	private String documento;
	private String nome;
	private String idProposta;
	private String resultadoSolicitacao;
	
	public String getResultado() {
		return resultado;
	}
	
	
	
	public CartaoResponse(String resultado) {
		super();
		this.resultado = resultado;
	}



	public String getDocumento() {
		return documento;
	}
	public String getNome() {
		return nome;
	}
	public String getIdProposta() {
		return idProposta;
	}
	public String getResultadoSolicitacao() {
		return resultadoSolicitacao;
	}
	public CartaoResponse(String documento, String nome, String idProposta, String resultadoSolicitacao) {
		this.documento = documento;
		this.nome = nome;
		this.idProposta = idProposta;
		this.resultadoSolicitacao = resultadoSolicitacao;
	}
	public CartaoResponse() {
		// TODO Auto-generated constructor stub
	}
	
}
