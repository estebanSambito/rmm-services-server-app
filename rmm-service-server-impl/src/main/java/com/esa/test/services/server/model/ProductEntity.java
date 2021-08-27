package com.esa.test.services.server.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.esa.test.services.server.model.enums.ProductTypes;

/**
 * 
 * @author esalazar
 *
 */
@Entity
@Table(name = "rmm_product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pr_id")
	private int idProd;
	@Column(name = "pr_name")
	private String name;
	//Service or product
	@Column(name = "pr_type")
//	@Enumerated(EnumType.STRING)
	private String type;
	
	@Column(name = "pr_cost")
	private BigDecimal cost;
	
	@OneToOne(mappedBy = "product", cascade = CascadeType.ALL,
             fetch = FetchType.LAZY, optional = false)
    private DetailProductEntity  details;
	

	public int getIdProd() {
		return idProd;
	}

	public void setIdProd(int idProd) {
		this.idProd = idProd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "ProductEntity [idProd=" + idProd + ", name=" + name + ", type=" + type + ", cost=" + cost + ", details="
				+ details + "]";
	}

	
}