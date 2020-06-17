package it.uniroma3.siw.taskmanager.controller.validators;

import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired
	CredentialsService credentialsService;
	
	final int MIN_USERNAME_LENGTH = 4;
	final int MAX_USERNAME_LENGTH = 20;
	final int MIN_PASSWORD_LENGTH = 4;
	final int MAX_PASSWORD_LENGTH = 20;
	
	
	@Override
	public void validate (Object o, Errors errors) {
		Credentials credentials = (Credentials) o;
		String username = credentials.getUsername().trim();
		String password = credentials.getPassword().trim();
		
		if (username.isEmpty())
			errors.rejectValue("username", "required");
		else if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
			errors.rejectValue("username", "size");
		else if (this.credentialsService.getCredentials(username) != null)
			errors.rejectValue("username", "duplicate");
		
		if (password.isEmpty())
			errors.rejectValue("password", "required");
		else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
			errors.rejectValue("password", "size");
	}


	@Override
	public boolean supports(Class<?> clazz) {

		return Credentials.class.equals(clazz);
	}

}
