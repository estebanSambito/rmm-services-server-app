package com.esa.test.services.server.model.enums;

/**
 * Permite diferenciar los productos entre producto o servicio
 * @author esalazar
 *
 */
public enum ProductTypes {
	
	DEVICE("DEV"),
	SERVICE("SERV");
	private String name;
	ProductTypes(String name){
		this.name = name;
	}
	 public String getName() {
	        return name;
	    }
}
