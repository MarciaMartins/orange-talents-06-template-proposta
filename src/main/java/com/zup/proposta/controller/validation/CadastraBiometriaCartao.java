package com.zup.proposta.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zup.proposta.modelo.Biometria;
import com.zup.proposta.modelo.Proposta;
import com.zup.proposta.repository.BiometriaRepository;
import com.zup.proposta.request.BiometriaRequest;

@Component
public class CadastraBiometriaCartao {

	@Autowired(required=true)
	private BiometriaRepository biometriaRepository;

	public Biometria cadastraBiometria(BiometriaRequest request, Proposta proposta) {
		
		String briometriaEncode = encriptografarBiometria(request.getBiometria());
		Biometria biometria = request.toModel(proposta, briometriaEncode);
		Biometria biometriaSalva = biometriaRepository.save(biometria);
		return biometriaSalva;

	}

	private String encriptografarBiometria(String biometria) {
		return new BCryptPasswordEncoder().encode(biometria);
	}

}
