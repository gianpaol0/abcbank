package ch.bestvision.abcbank.entity;

import java.util.Date;

import javax.validation.constraints.NotBlank;

public class ContactBaseTO {
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String secondName;
	
	private Date dateOfBirth;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
