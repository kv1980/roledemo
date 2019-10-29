package com.krist832.roledemo.repositories;

import java.util.Optional;

import com.krist832.roledemo.entities.Country;
import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.entities.NodeCountryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeCountryRepository extends JpaRepository<NodeCountry, NodeCountryId> {

	Optional<NodeCountry> findByNodeAndCountry(Node node, Country country);
}
