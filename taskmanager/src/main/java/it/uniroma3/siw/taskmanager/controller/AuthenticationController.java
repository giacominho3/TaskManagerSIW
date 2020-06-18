package it.uniroma3.siw.taskmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validators.CredentialsValidator;
import it.uniroma3.siw.taskmanager.controller.validators.UserValidator;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;


@Controller
public class AuthenticationController {
	
	@Autowired 
	public CredentialsService credentialsService;
	
	@Autowired
	public UserValidator userValidator;
	
	@Autowired 
	public CredentialsValidator credentialsValidator;
	
	@Autowired
	SessionData sessionData;
	
	
	@RequestMapping (value = {"/", "/users/register"}, method = RequestMethod.GET)
	public String showRegisterForm (Model model) {
		model.addAttribute("userForm", new User());
		model.addAttribute("credentialsForm", new Credentials());
		
		return "registerUser";
	}
	
	
	@RequestMapping (value = {"/users/register"}, method = RequestMethod.POST)
	public String registerUser (@Valid @ModelAttribute ("userForm") User user,
			BindingResult userBindingResult,
			@Valid @ModelAttribute ("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		
		this.userValidator.validate (user, userBindingResult);
		this.credentialsValidator.validate (credentials, credentialsBindingResult);
		
		if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			
			return "registrationSuccessfull";
	}
		return "registerUser";
	}
	
	@RequestMapping (value = {"/login"}, method = RequestMethod.GET)
	public String loginDisplay (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials", credentials);
		
		return "userLogin";
	}
	
	
	@RequestMapping (value = {"/login"}, method = RequestMethod.POST)
	public String processLogin (@Valid @ModelAttribute ("credentialsForm") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {
		
		this.credentialsValidator.validate (credentials, credentialsBindingResult);
		this.credentialsValidator.isCorrect (credentials);
		
		if (credentialsBindingResult.hasErrors()|| )
		
	}
	
}
