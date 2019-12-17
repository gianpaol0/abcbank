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

@Entity
@Table(name="CONTACT_PHONE_NUMBERS")
public class PhoneNumber {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private Long id;
	
	@Column
	@NotBlank
	private String type;
	
	@Column
	@NotBlank
	private String number;
	
	@ManyToOne
	@JoinColumn(name="CONTACT_ID")
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private Contact contact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
