package it.uniroma3.siw.taskmanager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired 
	public CredentialsRepository credentialsRepository;
	
	
	@Transactional
	public Credentials getCredentials (Long id) {
		Optional <Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
		
	@Transactional
	public Credentials getCredentials (String username) {
		Optional <Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials (Credentials credentials) {
		credentials.setRole(Credentials.DEFAULT_ROLE);
		return this.credentialsRepository.save(credentials);
	}

}
