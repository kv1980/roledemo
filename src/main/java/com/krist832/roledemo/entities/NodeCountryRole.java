package com.krist832.roledemo.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "nodecountyrole")
@Table(name = "roles")
@NoArgsConstructor
@Getter
@ToString
public class NodeCountryRole extends AbstractRole {

	@ManyToOne
	@JoinTable(name = "node_country_roles", joinColumns = {@JoinColumn(name = "role_id")},
			   inverseJoinColumns = {@JoinColumn(name = "node_id", referencedColumnName = "node_id"), @JoinColumn(name = "country_id", referencedColumnName = "country_id")})
				//name = column in table node_countries <-> referencedColumnName = name in table node_country_roles
	@ToString.Exclude
	private NodeCountry nodeCountry;

	public NodeCountryRole(final NodeCountry nodeCountry, final Employee employee, final String name) {
		super(name, employee);
		this.nodeCountry = nodeCountry;
	}
}