package com.ecommerce.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cart")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","user"})
public class Cart {
	@Id @GeneratedValue
	@Column(name="cartid")
	private long cartid;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userid", referencedColumnName = "userid")
	@JsonIgnoreProperties("cart")
	private User user;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
	@JsonIgnoreProperties("cart")
    private List<CartItems> cartitems;
	
	@Column(name="totalprice",columnDefinition = "DECIMAL(10,2) default 0")
	private float totalprice;

	public long getCartid() {
		return cartid;
	}

	public void setCartid(long cartid) {
		this.cartid = cartid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItems> getCartitems() {
		return cartitems;
	}

	public void setCartitems(List<CartItems> cartitems) {
		this.cartitems = cartitems;
	}

	public float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(float totalprice) {
		this.totalprice = totalprice;
	}
	
	
}
