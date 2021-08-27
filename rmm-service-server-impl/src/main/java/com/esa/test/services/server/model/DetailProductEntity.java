package com.esa.test.services.server.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author esalazar
 *
 */
@Entity
@Table(name = "rmm_detail")
public class DetailProductEntity {

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "dt_id", insertable=true, updatable=true, unique=true, nullable=false)
	private int idDetail;
	
	@OneToOne(fetch = FetchType.LAZY)
//    @MapsId
	@JoinColumn(name = "product_id", referencedColumnName = "pr_id")
	private ProductEntity product;
	
	@ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH}, fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_invoice", nullable = false, updatable = false)
	private InvoiceEntity invoice;
	
	
	@Column(name = "dt_quantity",nullable = false)
	private int quantity;


	public int getIdDetail() {
		return idDetail;
	}


	public void setIdDetail(int idDetail) {
		this.idDetail = idDetail;
	}


	public ProductEntity getProduct() {
		return product;
	}


	public void setProduct(ProductEntity product) {
		this.product = product;
	}


	public InvoiceEntity getInvoice() {
		return invoice;
	}


	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	@Override
	public String toString() {
		return "DetailProductEntity [idDetail=" + idDetail +", quantity=" + quantity + "]";
	}
	
		
}