package com.krist832.roledemo.services;

import java.util.Collection;
import java.util.UUID;

import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.entities.NodeCountry;
import com.krist832.roledemo.repositories.NodeCountryRepository;
import com.krist832.roledemo.repositories.NodeCountryRoleRepository;
import com.krist832.roledemo.repositories.NodeRepository;
import com.krist832.roledemo.repositories.NodeRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/V1__insertTables.sql", "/V2__insert_basic_examples.sql"})
@Import(NodeService.class)
public class NodeServiceIT {

	private static final UUID NODE_EUROPE_ID = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");

	private static final UUID COUNTRY_BELGIUM_ID = UUID.fromString("3f5ba304-7022-4a1a-a6e8-b28afca882d1");

	private static final UUID ROLE_PALLEMANS_AS_MANAGER_ID = UUID.fromString("71dba9a1-2352-4170-9bc0-5ad08b2d1bfe");

	private static final UUID ROLE_VANDAM_AS_REPRESENTATIVE_ID = UUID.fromString(
			"5ca7c3a5-4fec-4bd3-b89f-9e9516156032");

	private static final UUID EMPLOYEE_FRANKIE_LOOSVELD_ID = UUID.fromString("ffcf3efb-8132-4922-bceb-49f274fb364b");

	@Autowired
	NodeService nodeService;

	@Autowired
	NodeRepository nodeRepository;

	@Autowired
	NodeRoleRepository nodeRoleRepository;

	@Autowired
	NodeCountryRepository nodeCountryRepository;

	@Autowired
	NodeCountryRoleRepository nodeCountryRoleRepository;

	private long numberOfRecordsBeforeTest;

	//----------------------------------------------------CRUD-NODE-----------------------------------------------------

	@Test
	public void createNode() {
		numberOfRecordsBeforeTest = nodeRepository.count();

		UUID newId = nodeService.createNode("Asia");

		assertThat(newId).isNotNull();
		assertThat(nodeRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void getOneById() {
		Node actual = nodeService.getNode(UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd"));
		assertThat(actual).isNotNull();
	}

	//----------------------------------------------------NODE-ROLES----------------------------------------------------

	@Test
	public void addRoleToNode() {
		numberOfRecordsBeforeTest = nodeRoleRepository.count();

		nodeService.addRoleToNode(NODE_EUROPE_ID, EMPLOYEE_FRANKIE_LOOSVELD_ID, "handling responsible");

		assertThat(nodeRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void updateRoleOfNode() {
		// case manager of Europe must change from Guido Pallemans to Frankie Loosveld
		Node node = nodeService.updateRoleOfNode(NODE_EUROPE_ID, ROLE_PALLEMANS_AS_MANAGER_ID,
												 EMPLOYEE_FRANKIE_LOOSVELD_ID);

		assertThat(node.getNodeRoles()
					   .stream()
					   .filter(nodeRole -> nodeRole.getName()
												   .equals("region manager"))
					   .map(nodeRole -> nodeRole.getEmployee()
												.getName())).anyMatch(name -> name.equals("Frankie Loosveld"));
	}

	@Test
	public void removeRoleFromNode() {
		numberOfRecordsBeforeTest = nodeRoleRepository.count();

		nodeService.removeRoleFromNode(NODE_EUROPE_ID, ROLE_PALLEMANS_AS_MANAGER_ID);

		assertThat(nodeRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest - 1);
	}

	//----------------------------------------------------COUNTRIES-----------------------------------------------------

	@Test
	public void addCountryToNode() {
		numberOfRecordsBeforeTest = nodeCountryRepository.count();
		UUID countryFranceId = UUID.fromString("7ba9d3a4-0ede-4e86-a91d-0f42594d77c2");

		nodeService.addCountryToNode(NODE_EUROPE_ID, countryFranceId);

		assertThat(nodeCountryRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void removeCountryFromNode() {
		numberOfRecordsBeforeTest = nodeCountryRepository.count();

		nodeService.removeCountryFromNode(NODE_EUROPE_ID, COUNTRY_BELGIUM_ID);

		assertThat(nodeCountryRepository.count()).isEqualTo(numberOfRecordsBeforeTest - 1);
	}

	//--------------------------------------------------COUNTRY-ROLES---------------------------------------------------

	@Test
	public void addRoleToNodeCountry() {
		numberOfRecordsBeforeTest = nodeCountryRoleRepository.count();

		nodeService.addRoleToNodeCountry(NODE_EUROPE_ID, COUNTRY_BELGIUM_ID, EMPLOYEE_FRANKIE_LOOSVELD_ID,
										 "sales representative");

		assertThat(nodeCountryRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void updateRoleOfNodeCountry() {
		// case sales representative of country Belgium in node Europe must change from Alain Vandam to Frankie Loosveld
		Node node = nodeService.updateRoleOfNodeCountry(NODE_EUROPE_ID, COUNTRY_BELGIUM_ID,
														ROLE_VANDAM_AS_REPRESENTATIVE_ID, EMPLOYEE_FRANKIE_LOOSVELD_ID);

		assertThat(node.getNodeCountries()
					   .stream()
					   .filter(nodeCountry -> nodeCountry.getCountry()
														 .getName()
														 .equals("Belgium"))
					   .map(NodeCountry::getNodeCountryRoles)
					   .flatMap(Collection::stream)
					   .filter(nodeCountryRole -> nodeCountryRole.getName()
																 .equals("sales representative"))
					   .map(nodeCountryRole -> nodeCountryRole.getEmployee()
															  .getName())).anyMatch(
				name -> name.equals("Frankie Loosveld"));
	}

	@Test
	public void removeRoleFromNodeCountry() {
		numberOfRecordsBeforeTest = nodeCountryRoleRepository.count();

		nodeService.removeRoleFromNodeCountry(NODE_EUROPE_ID, COUNTRY_BELGIUM_ID, ROLE_VANDAM_AS_REPRESENTATIVE_ID);

		assertThat(nodeCountryRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest - 1);
	}
}
