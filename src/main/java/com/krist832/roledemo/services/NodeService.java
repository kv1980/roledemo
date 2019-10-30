package com.krist832.roledemo.services;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import com.krist832.roledemo.entities.Country;
import com.krist832.roledemo.entities.Employee;
import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.entities.NodeCountryRole;
import com.krist832.roledemo.entities.NodeRole;
import com.krist832.roledemo.repositories.CountryRepository;
import com.krist832.roledemo.repositories.EmployeeRepository;
import com.krist832.roledemo.repositories.NodeCountryRepository;
import com.krist832.roledemo.repositories.NodeCountryRoleRepository;
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

	private final NodeCountryRoleRepository nodeCountryRoleRepository;

	//----------------------------------------------------CRUD-NODE-----------------------------------------------------

	public UUID createNode(String name) {
		Node node = new Node(name);
		nodeRepository.save(node);
		return node.getId();
	}

	public Node getNode(UUID id) {
		return this.nodeRepository.getOne(id);
	}

	//----------------------------------------------------NODE-ROLES----------------------------------------------------

	public void addRoleToNode(UUID nodeId, UUID employeeId, String name){
		Node node = nodeRepository.getOne(nodeId);
		Employee employee = employeeRepository.getOne(employeeId);

		NodeRole nodeRole = new NodeRole(node, employee, name);

		nodeRoleRepository.save(nodeRole);
	}

	public Node updateRoleOfNode(UUID nodeId, UUID roleId, UUID employeeId){
		NodeRole nodeRole = this.getRoleOfNode(nodeId, roleId);
		Employee employee = employeeRepository.getOne(employeeId);

		nodeRole.changeEmployee(employee);

		return nodeRoleRepository.save(nodeRole).getNode();
	}

	public void removeRoleFromNode(UUID nodeId, UUID roleId){
		NodeRole nodeRole = this.getRoleOfNode(nodeId, roleId);

		nodeRoleRepository.delete(nodeRole);
	}

	private NodeRole getRoleOfNode(UUID nodeId, UUID roleId){
		Node node = nodeRepository.getOne(nodeId);
		return nodeRoleRepository.findByNodeAndId(node, roleId)
								 .orElseThrow(() -> new EntityNotFoundException("NodeRole not found"));
	}

	//----------------------------------------------------COUNTRIES-----------------------------------------------------

	public void addCountryToNode(UUID nodeId, UUID countryId) {
		Country country = countryRepository.getOne(countryId);
		Node node = nodeRepository.getOne(nodeId);

		NodeCountry nodeCountry = new NodeCountry(node, country);

		nodeCountryRepository.save(nodeCountry);
	}

	public void removeCountryFromNode(UUID nodeId, UUID countryId) {
		NodeCountry nodeCountry = this.getCountryOfNode(nodeId, countryId);

		nodeCountryRepository.delete(nodeCountry);
	}

	private NodeCountry getCountryOfNode(UUID nodeId, UUID countryId) {
		Country country = countryRepository.getOne(countryId);
		Node node = nodeRepository.getOne(nodeId);

		return nodeCountryRepository.findByNodeAndCountry(node, country)
									.orElseThrow(() -> new EntityNotFoundException("nodeCountry not found"));
	}

	//------------------------------------------------NODE-COUNTRY-ROLES------------------------------------------------

	public void addRoleToNodeCountry(UUID nodeId, UUID countryId, UUID employeeId, String name) {
		NodeCountry nodeCountry = this.getCountryOfNode(nodeId, countryId);
		Employee employee = employeeRepository.getOne(employeeId);

		NodeCountryRole nodeCountryRole = new NodeCountryRole(nodeCountry, employee, name);

		nodeCountryRoleRepository.save(nodeCountryRole);
	}

	public Node updateRoleOfNodeCountry(UUID nodeId, UUID countryId, UUID roleId, UUID employeeId){
		NodeCountryRole nodeCountryRole = this.getRoleOfNodeCountry(nodeId, countryId, roleId);
		Employee employee = employeeRepository.getOne(employeeId);

		nodeCountryRole.changeEmployee(employee);

		return nodeCountryRoleRepository.save(nodeCountryRole).getNodeCountry().getNode();
	}

	public void removeRoleFromNodeCountry(UUID nodeId, UUID countryId, UUID roleId) {
		NodeCountryRole nodeCountryRole = this.getRoleOfNodeCountry(nodeId, countryId, roleId);

		nodeCountryRoleRepository.delete(nodeCountryRole);
	}

	private NodeCountryRole getRoleOfNodeCountry(UUID nodeId, UUID countryId, UUID roleId) {
		NodeCountry nodeCountry = this.getCountryOfNode(nodeId, countryId);

		return nodeCountryRoleRepository.findByNodeCountryAndId(nodeCountry, roleId)
										.orElseThrow(() -> new EntityNotFoundException("NodeCountryRole not found"));
	}

}
