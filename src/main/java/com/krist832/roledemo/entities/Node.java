package com.krist832.roledemo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "nodes")
@NoArgsConstructor
@Getter
@ToString
public class Node {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	private String name;

	@OneToMany(mappedBy = "node")
	private Set<NodeRole> nodeRoles;

	@OneToMany(mappedBy = "node")
	private Set<NodeCountry> nodeCountries;

	public Node(String name) {
		this.name = name;
		this.nodeRoles = new HashSet<>();
		this.nodeCountries = new HashSet<>();
	}

	public Set<Country> getCountries() {
		return this.getNodeCountries()
				   .stream()
				   .map(NodeCountry::getCountry)
				   .collect(Collectors.toSet());
	}
}
