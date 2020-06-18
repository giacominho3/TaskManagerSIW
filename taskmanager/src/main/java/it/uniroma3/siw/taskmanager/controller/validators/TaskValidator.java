package it.uniroma3.siw.taskmanager.controller.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.repository.CredentialsRepository;

@Component
public class TaskValidator implements Validator {
	
	
	final int MAX_NAME_LENGTH = 40;
	final int MIN_NAME_LENGTH = 2;
	final int MAX_DESCRIPTION_LENGTH = 100;
	
	@Autowired
	CredentialsRepository credentialsRepository;
	
	@Override
	public void validate (Object o, Errors errors) {
		Task task = (Task) o;
		String name = task.getName().trim();
		String description = task.getDescription().trim();
		
		if (name.isEmpty())
			errors.rejectValue("name", "required");
		else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			errors.rejectValue("name", "size");
		
		if (description.length() > MAX_DESCRIPTION_LENGTH)
			errors.rejectValue("description", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		
		return Task.class.equals(clazz);
	}

}
