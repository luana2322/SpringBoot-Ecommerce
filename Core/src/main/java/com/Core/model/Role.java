package com.Core.model;

import java.util.Date;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "role")
public class Role {

	@Id
	@SequenceGenerator(name = "role_sequence", sequenceName = "role_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "role_sequence")
	private Long role_id;
	
	private String name;
	private String description;
	private Date created_At;
	private Date updated_At;
	
	 
	@ManyToMany(mappedBy = "roles")
	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	private Set<Users> user = new HashSet<>();

	public Role(Long role_id, String name, String description) {
		this.role_id = role_id;
		this.name = name;
		this.description = description;

	}

	public Role(String name) {
		this.name = name;
	}
	
	@PrePersist
	protected void onCreate() {
		this.created_At=new Date(System.currentTimeMillis());
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updated_At=new Date(System.currentTimeMillis());
	}

}
