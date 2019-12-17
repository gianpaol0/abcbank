package ch.bestvision.abcbank.entity;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModelProperty.AccessMode;

public class ContactTO extends ContactBaseTO {
	
	@ApiModelProperty(accessMode = AccessMode.READ_ONLY)
	private Long id;
	
	private List<AddressTO> addresses;
	
	private List<PhoneNumberTO> phoneNumbers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<AddressTO> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressTO> addresses) {
		this.addresses = addresses;
	}

	public List<PhoneNumberTO> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumberTO> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	
}
