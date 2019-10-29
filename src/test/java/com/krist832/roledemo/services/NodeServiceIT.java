package com.krist832.roledemo.services;

import java.util.UUID;

import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.repositories.NodeCountryRepository;
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

	@Autowired
	NodeService nodeService;

	@Autowired
	NodeRepository nodeRepository;

	@Autowired
	NodeCountryRepository nodeCountryRepository;

	@Autowired
	NodeRoleRepository nodeRoleRepository;

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
		Node actual = nodeService.getNodeById(UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd"));
		assertThat(actual).isNotNull();
	}

	//----------------------------------------------------NODE-ROLES----------------------------------------------------

	@Test
	public void addRoleToNode(){
		numberOfRecordsBeforeTest = nodeRoleRepository.count();
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID employeeFrankieLoosveldId = UUID.fromString("ffcf3efb-8132-4922-bceb-49f274fb364b");

		nodeService.addRoleToNode(nodeEuropeId, employeeFrankieLoosveldId, "handling responsible");

		assertThat(nodeRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void updateRoleOfNode(){
		// case region manager of Europe must change from Guido Pallemans to Frankie Loosveld
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID roleRegionManagerId = UUID.fromString("71dba9a1-2352-4170-9bc0-5ad08b2d1bfe");
		UUID employeeFrankieLoosveldId = UUID.fromString("ffcf3efb-8132-4922-bceb-49f274fb364b");

		Node node = nodeService.updateRoleOfNode(roleRegionManagerId, nodeEuropeId, employeeFrankieLoosveldId);

		assertThat(node.getNodeRoles()
					   .stream()
					   .filter(nodeRole -> nodeRole.getName().equals("region manager"))
				       .map(nodeRole -> nodeRole.getEmployee().getName()))
					   .anyMatch(name -> name.equals("Frankie Loosveld"));
	}

	@Test
	public void removeRoleFromNode(){
		numberOfRecordsBeforeTest = nodeRoleRepository.count();
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID roleRegionManagerId = UUID.fromString("71dba9a1-2352-4170-9bc0-5ad08b2d1bfe");

		nodeService.removeRoleFromNode(roleRegionManagerId, nodeEuropeId);

		assertThat(nodeRoleRepository.count()).isEqualTo(numberOfRecordsBeforeTest - 1);
	}

	//----------------------------------------------------COUNTRIES-----------------------------------------------------

	@Test
	public void addCountryToNode() {
		numberOfRecordsBeforeTest = nodeCountryRepository.count();
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID countryFranceId = UUID.fromString("7ba9d3a4-0ede-4e86-a91d-0f42594d77c2");

		nodeService.addCountryToNode(countryFranceId, nodeEuropeId);

		assertThat(nodeCountryRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

	@Test
	public void removeCountryFromNode() {
		numberOfRecordsBeforeTest = nodeCountryRepository.count();
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID countryBelgiumId = UUID.fromString("3f5ba304-7022-4a1a-a6e8-b28afca882d1");

		nodeService.removeCountryFromNode(countryBelgiumId, nodeEuropeId);

		assertThat(nodeCountryRepository.count()).isEqualTo(numberOfRecordsBeforeTest - 1);
	}

	//--------------------------------------------------COUNTRY-ROLES---------------------------------------------------



}
