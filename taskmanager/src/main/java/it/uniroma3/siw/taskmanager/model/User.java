package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class User {
	
	
	//campi 
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false, length = 100)
	private String firstName;
	
	@Column (nullable = false, length = 100)
	private String lastName;
	
	@Column (updatable = false, nullable = false)
	private LocalDateTime creationTimeStamp;
	
	@Column (nullable = false)
	private LocalDateTime updateTimeStamp;
	
	
	//riferimenti
	
	@OneToMany (cascade = CascadeType.REMOVE, mappedBy = "owner")
	private List <Project> ownedProjects;
	
	@ManyToMany (mappedBy = "members")
	private List <Project> visibleProjects;
	
	@OneToMany (cascade = CascadeType.REMOVE, mappedBy = "assigned")
	private List <Task> assignedTasks;
	
	
	
	//costruttore vuoto
	
	public User () {
		
		this.ownedProjects = new ArrayList <>();
		this.visibleProjects = new ArrayList <>();
	}
	
	//costruttore parametrico
	
	public User (String username, String password, String firstName, String lastName) {
		
		this();
		this.firstName = firstName;
		this.lastName = lastName;
		this.creationTimeStamp = LocalDateTime.now();
		this.updateTimeStamp = LocalDateTime.now();
	}
	
	
	
	//metodi getter e setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public LocalDateTime getCreationTimeStamp() {
		return creationTimeStamp;
	}
	
	public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
		this.creationTimeStamp = creationTimeStamp;
	}
	
	public LocalDateTime getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	
	public void setUpdateTimeStamp(LocalDateTime updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
	
	public List<Project> getOwnedProjects() {
		return ownedProjects;
	}
	
	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}
	
	public List<Project> getVisibleProjects() {
		return visibleProjects;
	}
	
	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}
	
	public List<Task> getAssignedTasks() {
		return assignedTasks;
	}

	public void setAssignedTasks(List<Task> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}


	
	//toString
	
	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", creationTimeStamp=" + creationTimeStamp + ", updateTimeStamp=" + updateTimeStamp
				+ ", ownedProjects=" + ownedProjects + ", visibleProjects=" + visibleProjects + "]";
	}

	
	
	//metodi per la gestione delle date e degli orari di creazione e modifica
	
	@PrePersist 
	protected void onPersist () {
		this.creationTimeStamp = LocalDateTime.now();
		this.updateTimeStamp = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate () {
		this.updateTimeStamp = LocalDateTime.now();
	}

	
	
	//hashcode e equals
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationTimeStamp == null) ? 0 : creationTimeStamp.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((updateTimeStamp == null) ? 0 : updateTimeStamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (updateTimeStamp == null) {
			if (other.updateTimeStamp != null)
				return false;
		} else if (!updateTimeStamp.equals(other.updateTimeStamp))
			return false;
		return true;
	}
	
}
