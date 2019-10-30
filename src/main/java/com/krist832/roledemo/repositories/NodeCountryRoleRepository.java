package com.krist832.roledemo.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.entities.NodeCountryRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeCountryRoleRepository extends JpaRepository<NodeCountryRole, UUID> {

	Optional<NodeCountryRole> findByNodeCountryAndId(NodeCountry nodeCountry, UUID roleId);

	List<NodeCountryRole> findByNodeCountry(NodeCountry nodeCountry);
}
