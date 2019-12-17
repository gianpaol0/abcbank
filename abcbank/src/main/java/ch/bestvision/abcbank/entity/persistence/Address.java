package ch.bestvision.abcbank.entity.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;
import io.swagger.annotations.ApiParam;

@Entity
@Table(name="CONTACT_ADDRESSES")
public class Address {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private Long id;
	
	@Column
	@NotBlank
	private String street;
	
	@Column
	private String streetNumber;
	
	@Column
	private String postalCode;
	
	@Column
	@NotBlank
	private String city;
	
	//TODO : Create a Country Object...
	@Column
	private String country;
	
	@ManyToOne
    @JoinColumn(name = "CONTACT_ID", nullable = false)
	@JsonIgnore
	@ApiModelProperty(hidden = true, accessMode = AccessMode.READ_ONLY)
	@ApiParam(hidden = true)
	private Contact contact;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@JsonIgnore
	public Contact getContact() {
		return contact;
	}
	
	@JsonIgnore
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
}
