package com.krist832.roledemo.services;

import java.util.UUID;

import com.krist832.roledemo.entities.Node;
import com.krist832.roledemo.repositories.NodeCountryRepository;
import com.krist832.roledemo.repositories.NodeRepository;
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

	private long numberOfRecordsBeforeTest;

	@Test
	public void createNode() {
		numberOfRecordsBeforeTest = nodeRepository.count();

		UUID newId = nodeService.createNode("Asia");

		assertThat(newId).isNotNull();
		assertThat(nodeRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}

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

	@Test
	public void addRoleToNode() {
		numberOfRecordsBeforeTest = nodeCountryRepository.count();
		UUID nodeEuropeId = UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd");
		UUID countryFranceId = UUID.fromString("7ba9d3a4-0ede-4e86-a91d-0f42594d77c2");

		nodeService.addCountryToNode(countryFranceId, nodeEuropeId);

		assertThat(nodeCountryRepository.count()).isEqualTo(numberOfRecordsBeforeTest + 1);
	}



	@Test
	public void getOneById() {
		Node actual = nodeService.getNodeById(UUID.fromString("2234cf74-9a26-4772-94a8-adb730b5b4cd"));
		assertThat(actual).isNotNull();
	}
}
