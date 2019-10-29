package com.krist832.roledemo.repositories;

import java.util.UUID;

import com.krist832.roledemo.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, UUID> {}
