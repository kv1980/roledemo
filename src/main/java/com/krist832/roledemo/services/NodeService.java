package com.krist832.roledemo.services;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import com.krist832.roledemo.entities.Country;
import com.krist832.roledemo.entities.Employee;
import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.entities.NodeRole;
import com.krist832.roledemo.repositories.CountryRepository;
import com.krist832.roledemo.repositories.EmployeeRepository;
import com.krist832.roledemo.repositories.NodeCountryRepository;
import com.krist832.roledemo.repositories.NodeRepository;
import com.krist832.roledemo.repositories.NodeRoleRepository;
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

	private final EmployeeRepository employeeRepository;

	private final NodeRoleRepository nodeRoleRepository;

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

	public void addRoleToNode(UUID nodeId, UUID employeeId, String name){
		Node node = nodeRepository.getOne(nodeId);
		Employee employee = employeeRepository.getOne(employeeId);

		NodeRole nodeRole = new NodeRole(node, name, employee);

		nodeRoleRepository.save(nodeRole);
	}

	public Node updateRoleOfNode(UUID roleId, UUID nodeId, UUID employeeId){
		Node node = nodeRepository.getOne(nodeId);
		NodeRole nodeRole = nodeRoleRepository.findByNodeAndId(node, roleId)
											  .orElseThrow(() -> new EntityNotFoundException("NodeRole not found"));
		Employee employee = employeeRepository.getOne(employeeId);

		nodeRole.changeEmployee(employee);

		return nodeRoleRepository.save(nodeRole).getNode();
	}

	public void removeRoleFromNode(UUID roleId, UUID nodeId){
		Node node = nodeRepository.getOne(nodeId);
		NodeRole nodeRole = nodeRoleRepository.findByNodeAndId(node, roleId)
											  .orElseThrow(() -> new EntityNotFoundException("NodeRole not found"));

		nodeRoleRepository.delete(nodeRole);
	}

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

	//--------------------------------------------------COUNTRY-ROLES--------------------------------------------------



}
