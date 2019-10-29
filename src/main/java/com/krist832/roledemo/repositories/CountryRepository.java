package com.krist832.roledemo.repositories;

import java.util.UUID;

import com.krist832.roledemo.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {}
