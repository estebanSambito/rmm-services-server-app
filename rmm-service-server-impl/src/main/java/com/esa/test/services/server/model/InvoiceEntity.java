package com.esa.test.services.server.model;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author esalazar
 *
 */
@Entity
@Table(name = "rmm_invoice")
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "in_id")
	private int idBilling;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice")
//	@OneToMany(mappedBy = "invoice")
	private List<DetailProductEntity> products;

	@JoinColumn(name = "fk_user", nullable = false)
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private UserEntity user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "in_creation_date")
	private Calendar creationDate;

	@Column(name = "in_total",nullable = true)
	private BigDecimal total;
	
	@Column(name = "in_status", length = 20, nullable = true)
//    @Enumerated(EnumType.STRING)
    private String status;

	public int getIdBilling() {
		return idBilling;
	}

	public void setIdBilling(int idBilling) {
		this.idBilling = idBilling;
	}

	public List<DetailProductEntity> getProducts() {
		return products;
	}

	public void setProducts(List<DetailProductEntity> products) {
		this.products = products;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}