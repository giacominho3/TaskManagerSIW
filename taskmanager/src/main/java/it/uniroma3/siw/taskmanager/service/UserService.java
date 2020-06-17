package it.uniroma3.siw.taskmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.taskmanager.model.User;
import it.uniroma3.siw.taskmanager.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User getUser (Long id) {
		Optional <User> result = this.userRepository.findById(id);
		return result.orElse(null);
	}
	
	@Transactional
	public User getUser (String username) {
		Optional <User> result = this.userRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public User saveUser (User user) {
		return this.userRepository.save(user);
	}
	
	@Transactional
	public List <User> findAllUsers () {
		Iterable <User> i = this.userRepository.findAll();
		ArrayList<User> list = new ArrayList<>();
		for (User u : i) {
			list.add(u);
		}
		return list;
	}

}
