package it.uniroma3.siw.taskmanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validators.ProjectValidator;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.CredentialsService;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.UserService;
import it.uniroma3.siw.taskmanager.model.Credentials;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;

@Controller
public class ProjectController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	ProjectValidator projectValidator;
	
	@Autowired
	SessionData sessionData;
	
	@Autowired
	CredentialsService credentialsService;
	
	
	@RequestMapping (value = {"/projects"}, method = RequestMethod.GET)
	public String myOwnedProjects (Model model) {
		User loggedUser = this.sessionData.getLoggedUser();
		List <Project> projectsList = projectService.retrieveProjectsOwnedBy(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		
		return "myOwnedProjects";
	}
	
	@RequestMapping (value = {"/projects/{projectId}"}, method = RequestMethod.GET)
	public String project (Model model, @PathVariable Long projectId) {
		User loggedUser = this.sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		if (project == null)
			return "redirect:/projects";
		
		List <User> members = userService.getMembers(project);
		List <Task> tasks = project.getTasks();
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/projects";
		
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		model.addAttribute("tasks", tasks);
		model.addAttribute("credentialsForm", new Credentials ());
		
		return "project";
	}
	
	@RequestMapping (value = {"/projects/{projectId}/share"}, method = RequestMethod.POST)
	public String shareProject (@RequestParam("username") String username, @PathVariable Long id) {
		Project project = projectService.getProject(id);
		Credentials credentials = this.credentialsService.getCredentials(username);
		
		if (credentials != null) {
			this.projectService.shareProjectWithUser(project, credentials.getUser());
			
			return "redirect:/project/" + id;
		}
		
		return "redirect:/home";
	}
	
	
	
	@RequestMapping (value = {"/projects/add"}, method = RequestMethod.GET)
	public String createProjectForm (Model model) {
		User loggedUser = this.sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		
		return "addProject";
	}
	
	
	@RequestMapping (value = {"/projects/add"}, method = RequestMethod.POST)
	public String createProject (@Valid @ModelAttribute ("projectForm") Project project,
								BindingResult projectBindingResult,
								Model model) {
		User loggedUser = this.sessionData.getLoggedUser();
		
		projectValidator.validate (project, projectBindingResult);
		if (!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId(); 
		}
		
		model.addAttribute("loggedUser", loggedUser);
		
		return "addProject";

	}

}
