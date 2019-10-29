package com.krist832.roledemo.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "node_countries")
@NoArgsConstructor
@Getter
@ToString
public class NodeCountry {

	@EmbeddedId
	private NodeCountryId nodeCountryId;

	@ManyToOne
	@MapsId("node_id")
	@ToString.Exclude
	private Node node;

	@ManyToOne
	@MapsId("country_id")
	private Country country;

	@OneToMany(mappedBy = "nodeCountry", orphanRemoval = true)
	Set<NodeCountryRole> nodeCountryRoles;

	public NodeCountry(final Node node, final Country country) {
		this.nodeCountryId = new NodeCountryId(node.getId(), country.getId());
		node.getNodeCountries().add(this);
		nodeCountryRoles = new HashSet<>();
	}
}
