package it.uniroma3.siw.taskmanager.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.taskmanager.controller.session.SessionData;
import it.uniroma3.siw.taskmanager.controller.validators.TaskValidator;
import it.uniroma3.siw.taskmanager.model.Project;
import it.uniroma3.siw.taskmanager.model.Task;
import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.service.ProjectService;
import it.uniroma3.siw.taskmanager.service.TaskService;

@Controller
public class TaskController {
	
	@Autowired
	SessionData sessionData;
	
	@Autowired 
	TaskService taskService;
	
	@Autowired
	TaskValidator taskValidator;
	
	@Autowired
	ProjectService projectService;
	
	
	@RequestMapping (value = {"/projects/{projectId}/addTask"}, method = RequestMethod.GET)
	public String createTaskForm (Model model, @PathVariable Long projectId) {
		User loggedUser = this.sessionData.getLoggedUser();
		Project currentProject = this.projectService.getProject(projectId);
		model.addAttribute("currentProject", currentProject);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		
		return "createTask";
	}
	
	
	@RequestMapping (value = {"/projects/{projectId}/addTask"}, method = RequestMethod.POST)
	public String createTask (@Valid @ModelAttribute ("taskForm") Task task,
								BindingResult taskBindingResult,
								@PathVariable Long projectId,
								Model model) {
		
		User loggedUser = this.sessionData.getLoggedUser();
		Project project = this.projectService.getProject(projectId);
		
		this.taskValidator.validate (task, taskBindingResult);
		if (project == null) {
			if (!taskBindingResult.hasErrors()) {
				this.taskService.saveTask(task);
				project.addTask(task);
				projectService.saveProject(project);
				return "redirect:/projects/" + projectId.toString(); 
			}
		
		model.addAttribute("loggedUser", loggedUser);
		
		return "createTask";

}
	return "redirect:/projects/" + projectId.toString();
}
	
}
