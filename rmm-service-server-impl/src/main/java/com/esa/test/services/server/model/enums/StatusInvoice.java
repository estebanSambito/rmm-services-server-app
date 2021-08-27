package com.esa.test.services.server.model.enums;

public enum StatusInvoice {

	OPEN("O"),
	CLOSED("C");
	private String name;
	StatusInvoice(String name){
		this.name = name;
	}
	 public String getName() {
	        return name;
	    }
}
