package com.krist832.roledemo.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@Getter
@ToString
public class NodeRole extends AbstractRole {

	@ManyToOne
	@JoinTable(name = "node_roles", joinColumns = {@JoinColumn(name = "role_id")},
			   inverseJoinColumns = {@JoinColumn(name = "node_id")})
	@ToString.Exclude
	private Node node;

	public NodeRole(final Node node, final String name, final Employee employee ) {
		super(name, employee);
		this.node = node;
		node.getNodeRoles().add(this);
	}
}
