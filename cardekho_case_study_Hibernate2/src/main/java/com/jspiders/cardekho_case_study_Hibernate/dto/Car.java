package com.jspiders.cardekho_case_study_Hibernate.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Car {

	@Id
	private int car_id;
	private String name;
	private String brand;
	private String fuel_type;
	private double price;
} 
 