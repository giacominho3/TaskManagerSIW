package it.uniroma3.siw.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.model.User;

@Controller
public class UserController {
	
	@Autowired
	SessionData sessionData;
	
	@RequestMapping (value = {"/home"}, method = RequestMethod.GET)
	public String home (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "home";	
	}
	
	@RequestMapping (value = {"/admin"}, method = RequestMethod.GET)
	public String admin (Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "admin";	
	}

}
