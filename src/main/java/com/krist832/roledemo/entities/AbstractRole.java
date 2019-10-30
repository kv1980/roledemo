package com.krist832.roledemo.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class AbstractRole {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	private String name;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	AbstractRole(final String name, final Employee employee) {
		this.name = name;
		this.employee = employee;
	}

	public void changeEmployee(final Employee employee) {
		this.employee = employee;
	}
}
