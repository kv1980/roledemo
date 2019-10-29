package com.krist832.roledemo.repositories;

import java.util.Optional;
import java.util.UUID;

import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeRoleRepository extends JpaRepository<NodeRole, UUID> {

	Optional<NodeRole> findByNodeAndId(Node node, UUID id);
}
