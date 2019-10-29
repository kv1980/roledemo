package com.krist832.roledemo.repositories;

import java.util.UUID;

import com.krist832.roledemo.entities.NodeCountryRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NodeCountryRoleRepository extends JpaRepository<NodeCountryRole, UUID> {}
