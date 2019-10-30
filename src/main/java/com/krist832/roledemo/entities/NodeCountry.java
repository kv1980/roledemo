package com.krist832.roledemo.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "node_countries")
@NoArgsConstructor
@Getter
public class NodeCountry {

	@EmbeddedId
	private NodeCountryId nodeCountryId;

	@ManyToOne
	@MapsId("node_id")
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

	@Embeddable
	@NoArgsConstructor
	@AllArgsConstructor
	public static class NodeCountryId implements Serializable {

		@Column(name = "node_id")
		private UUID nodeId;

		@Column(name = "country_id")
		private UUID countryId;
	}
}
