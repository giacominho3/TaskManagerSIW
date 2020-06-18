package it.uniroma3.siw.taskmanager.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class Task {
	
	//campi
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;
	

	@Column (nullable = false, length = 100)
	private String name;
	
	@Column (nullable = false, length = 100)
	private String description;
	
	@Column (nullable = false)
	private boolean completed;
	
	@Column (updatable = false, nullable = false)
	private LocalDateTime creationTimeStamp;
	
	@Column (nullable = false)
	private LocalDateTime updateTimeStamp;
	
	
	//riferimenti
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private Credentials assigned;
	
	//costruttore vuoto
	
	public Task () {
		
		this.assigned = new Credentials();
	}
	
	
	//costruttore parametrico
	
	public Task (String name, String description, boolean completed, LocalDateTime creationTimeStamp, LocalDateTime updateTimeStamp) {
		
		super();
		this.name = name;
		this.description = description;
		this.completed = completed;
		this.creationTimeStamp = LocalDateTime.now();
		this.updateTimeStamp = LocalDateTime.now();
	}
	
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


	public Credentials getAssigned() {
		return assigned;
	}


	public void setAssigned(Credentials assigned) {
		this.assigned = assigned;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
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
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((creationTimeStamp == null) ? 0 : creationTimeStamp.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Task other = (Task) obj;
		if (completed != other.completed)
			return false;
		if (creationTimeStamp == null) {
			if (other.creationTimeStamp != null)
				return false;
		} else if (!creationTimeStamp.equals(other.creationTimeStamp))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
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
