package com.jspiders.cardekho_case_study_Hibernate.dao;

//Search Car By Brand 
//Search Car By Fuel Type 
//Database used : weja2cardekhohibernate
//We have created the database manually in MySql
//The table structure created by hibernate in database
//The insert/update/remove/fetch operations are done using methods provided in hibernate

import java.util.InputMismatchException;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jspiders.cardekho_case_study_Hibernate.dto.Car;

public class CarOperations {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;
	private static int result;
	
	private static void openConnection() {
		try { 
			entityManagerFactory=Persistence.createEntityManagerFactory("Car");
			entityManager=entityManagerFactory.createEntityManager();
			entityTransaction=entityManager.getTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static void closeConnection() {
		try {
			if (entityManagerFactory != null) {
				entityManagerFactory.close();
			}
			if (entityManager != null) {
				entityManager.close();
			}
			if (entityTransaction != null) {
				if(entityTransaction.isActive()) {
					entityTransaction.rollback();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCarDetails() {
		try {
			openConnection();
			entityTransaction.begin();
			Scanner scanner = new Scanner(System.in);
			System.out.println("How many car details"
					+ " you want to add?");
			System.out.println("enter car");
			int choice = scanner.nextInt();
			for (int i = 1; i<= choice ; i++) {
				System.out.println("Enter the details "
						+ "for car " + i);
				
				Car car = new Car();
			
				try {
					
					System.out.print("Enter car id : ");
					car.setCar_id(scanner.nextInt());
					
					System.out.print("Enter car name : ");
					car.setName(scanner.next());
					
					System.out.print("Enter car brand : ");
					car.setBrand(scanner.next());
					
					System.out.print("Enter car fuel type : ");
					car.setFuel_type(scanner.next());
					
					System.out.print("Enter car price : ");
					car.setPrice(scanner.nextDouble());
					
					entityManager.persist(car);
					
				} catch (InputMismatchException e) {
					System.out.println("Invalid input!!! Data not inserted!");
					break;
				}
				
			}
			entityTransaction.commit();
			System.out.println("Car added successfully. ");
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void removeCarDetails() {
		try {
			openConnection();
			entityTransaction.begin();
			System.out.print("\nEnter car id to remove : ");
			Scanner scanner = new Scanner(System.in);
			int choice = scanner.nextInt();
			Car car = entityManager.find(Car.class, choice);
			try{
				entityManager.remove(car);
				System.out.println("Car no. " + choice + " Removed successfully");
				}catch(IllegalArgumentException e){
					System.out.println("Entered Car ID is not available ");
				}
			
			entityTransaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void searchById() {
		try {
			openConnection();
			entityTransaction.begin();//No need of this but we can use it
			System.out.print("Enter car ID : ");
			Scanner scanner = new Scanner(System.in);
			
			Car car_detail = entityManager.find(Car.class, scanner.nextInt());
			System.out.println(car_detail);
			entityTransaction.commit();//No need of this but we can use it
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
	public void searchByBrand() {
		try {
			openConnection();
			entityTransaction.begin();
			System.out.print("Enter car brand : ");
			Scanner scanner = new Scanner(System.in);
			String brand= scanner.next();
			Query query=entityManager.createQuery("Select card from Car card where card.brand IN(?1)");
			//where the brand in "card.brand" is the property from class
			query.setParameter(1, brand);
			List<Car> cars = query.getResultList();
			for(Car car : cars) {
				System.out.println(car);
			}
			
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void searchByFuelType() {
		try {
			openConnection();
			entityTransaction.begin();
			System.out.print("Enter car fuel type : ");
			Scanner scanner = new Scanner(System.in);
			String fuel_type= scanner.next();
			Query query=entityManager.createQuery("Select card from Car card where card.fuel_type IN(?1)");
			query.setParameter(1, fuel_type);
			List<Car> cars = query.getResultList();
			for(Car car : cars) {
				System.out.println(car);
			}
			
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	public void getAllCarDetails() {
		try {
			openConnection();
			entityTransaction.begin();
			Query query=entityManager.createQuery("Select card from Car card");
			List<Car> cars = query.getResultList();
			for(Car car : cars) {
				System.out.println(car);
			}
			
			entityTransaction.commit();
				

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	public void editCarDetails() {
		try {
//			getAllCarDetails();
			openConnection();
			entityTransaction.begin();
			System.out.print("Select car id to update : ");
			Scanner scanner = new Scanner(System.in);
			Car car = entityManager.find(Car.class, scanner.nextInt());
			System.out.println(car);
			System.out.print("Select option to update : \n"
					+ "1. Name\n"
					+ "2. Brand\n"
					+ "3. Fuel Type\n"
					+ "4. Price");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:				
				System.out.println("Enter new car name : ");
				car.setName(scanner.next());
				entityManager.persist(car);
				System.out.println("New Car name updated ");
				break;
			case 2:
				System.out.println("Enter new car brand : ");
				car.setBrand(scanner.next());
				entityManager.persist(car);
				System.out.println("New Car brand updated ");
				break;
				
			case 3:
				
				System.out.println("Enter new car fuel type : ");
				car.setFuel_type(scanner.next());
				entityManager.persist(car);
				System.out.println("New Car fuel type updated ");
				break;
				
			case 4:
				System.out.println("Enter new car price : ");
				car.setPrice(scanner.nextDouble());
				entityManager.persist(car);
				System.out.println("New Car price updated ");
				break;

			default:
				System.out.println("Invalid choice. ");
				break;
			}
			entityTransaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}
	
}
