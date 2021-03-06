package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
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
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return this.credentialsRepository.save(credentials);
	}
	
	@Transactional
	public void deleteCredentials (Credentials credentials) {
		this.credentialsRepository.delete(credentials);
	}
	
	@Transactional
	public List <Credentials> getAllCredentials () {
		Iterable <Credentials> i = this.credentialsRepository.findAll();
		ArrayList<Credentials> list = new ArrayList<>();
		for (Credentials c : i) {
			list.add(c);
		}
		return list;
	}

}
