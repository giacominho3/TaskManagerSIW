package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public class Project {
	
	//campi
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	
	@Column (nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@Column (updatable = false, nullable = false)
	private LocalDateTime creationTimeStamp;
	
	@Column (nullable = false)
	private LocalDateTime updateTimeStamp;
	
	
	//riferimenti
	
	@ManyToOne (fetch = FetchType.EAGER)
	private User owner;
	
	@ManyToMany
	private List <User> members;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn (name = "projectId")
	private List <Task> tasks;
	
	
	
	//costruttore vuoto
	
	public Project () {
		
		this.members = new ArrayList <>();
		this.tasks = new ArrayList <>();
	}
	
	//costruttore parametrico
	
	public Project (Long id, String name, User owner) {
		
		this();
		this.id = id;
		this.name = name;
		this.owner = owner;
	}
	
	
	
	//metodi getter e setter

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addMember (User member) {
		this.members.add(member);
	}
	
	public void addTask (Task task) {
		this.tasks.add(task);
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Project other = (Project) obj;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updateTimeStamp == null) {
			if (other.updateTimeStamp != null)
				return false;
		} else if (!updateTimeStamp.equals(other.updateTimeStamp))
			return false;
		return true;
	}
	
}
