package com.krist832.roledemo.repositories;

import java.util.UUID;

import com.krist832.roledemo.entities.AbstractRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AbstractRole, UUID> {}
