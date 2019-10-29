package com.krist832.roledemo.services;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import com.krist832.roledemo.entities.Country;
import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.repositories.CountryRepository;
import com.krist832.roledemo.repositories.NodeCountryRepository;
import com.krist832.roledemo.repositories.NodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
@RequiredArgsConstructor
public class NodeService {

	private final NodeRepository nodeRepository;

	private final NodeCountryRepository nodeCountryRepository;

	private final CountryRepository countryRepository;

	//----------------------------------------------------CRUD-NODE-----------------------------------------------------

	public UUID createNode(String name) {
		Node node = new Node(name);
		nodeRepository.save(node);
		return node.getId();
	}

	public Node getNodeById(UUID id) {
		return this.nodeRepository.getOne(id);
	}

	//----------------------------------------------------NODE-ROLES----------------------------------------------------






	//----------------------------------------------------COUNTRIES-----------------------------------------------------

	public void addCountryToNode(UUID countryId, UUID nodeId) {
		Country country = countryRepository.getOne(countryId);
		Node node = nodeRepository.getOne(nodeId);

		NodeCountry nodeCountry = new NodeCountry(node, country);

		nodeCountryRepository.save(nodeCountry);
	}

	public void removeCountryFromNode(UUID countryId, UUID nodeId) {
		Country country = countryRepository.getOne(countryId);
		Node node = nodeRepository.getOne(nodeId);

		NodeCountry nodeCountry = nodeCountryRepository.findByNodeAndCountry(node, country)
													   .orElseThrow(() -> new EntityNotFoundException(
															   "nodeCountry not found"));

		nodeCountryRepository.delete(nodeCountry);
	}


}
