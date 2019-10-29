package com.krist832.roledemo.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NodeCountryId implements Serializable {

	@Column(name = "node_id")
	private UUID nodeId;

	@Column(name = "country_id")
	private UUID countryId;
}
