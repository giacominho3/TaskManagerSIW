package it.uniroma3.siw.taskmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Credentials {
	
	//campi
	
	public static final String DEFAULT_ROLE = "DEFAULT";
	
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (unique = true, nullable = false, length = 100)
	private String username;
	
	@Column (nullable = false, length = 100)
	private String password;
	
	@Column (nullable = false, length = 10)
	private String role;
	
	
	//riferimenti
	
	@OneToOne (cascade = CascadeType.ALL)
	private User user;


	//metodi getters e setters
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}

}
