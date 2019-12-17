package ch.bestvision.abcbank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.bestvision.abcbank.entity.AddressTO;
import ch.bestvision.abcbank.entity.ContactBaseTO;
import ch.bestvision.abcbank.entity.ContactCriteria;
import ch.bestvision.abcbank.entity.ContactTO;
import ch.bestvision.abcbank.entity.PhoneNumberTO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AbcBankApplication.class},webEnvironment=WebEnvironment.RANDOM_PORT)
@PropertySource("classpath:test.properties")
@TestMethodOrder(OrderAnnotation.class)
public class AbcBankApplicationTests {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	ObjectMapper objectMapper;
	
	HttpHeaders headers = new HttpHeaders();
	
	// CONTACT TEST
	@Test
	@Order(1)
	public void selectAllContacts() throws Exception{
			
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<ContactTO[]> response = restTemplate.exchange(
				createURLWithPort("/api/v1/contacts"),
				HttpMethod.GET, entity, ContactTO[].class);
		
		Assert.assertTrue(response.getBody().length == 1);
		
		Assert.assertEquals(response.getBody()[0].getFirstName(), "Gianpaolo");
		Assert.assertEquals(response.getBody()[0].getSecondName(), "Altamura");
	}
	
	@Test
	@Order(2)
	public void insertContact() throws Exception{
				
		// Create contact object
		ContactTO contact = new ContactTO();
		contact.setFirstName("Dante");
		contact.setSecondName("Alighieri");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		contact.setDateOfBirth(formatter.parse("24-10-1978"));
		
		AddressTO address = new AddressTO();
		address.setCity("Firenze");
		address.setPostalCode("50100");
		address.setStreet("Via Divina Commedia");
		address.setStreetNumber("1");
		// address.setId(1000L);
		address.setCountry("Italy");
		List<AddressTO> addresses = new ArrayList<>();
		addresses.add(address);
		contact.setAddresses(addresses);
		
		PhoneNumberTO phoneNumber = new PhoneNumberTO();
		phoneNumber.setType("cell");
		phoneNumber.setNumber("+4455555555");
		List<PhoneNumberTO> phoneNumbers = new ArrayList<>();
		phoneNumbers.add(phoneNumber);
		contact.setPhoneNumbers(phoneNumbers);

		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ContactTO> entity = new HttpEntity<>(contact, headers);

		RestTemplate restTemplate = new RestTemplate();

		ContactTO response = restTemplate.postForObject(createURLWithPort("/api/v1/contacts"), 
						entity, 
						ContactTO.class);
				
		Assert.assertEquals(response.getFirstName(), "Dante");
		//address.id is not 1000
		Assert.assertNotEquals(response.getAddresses().get(0).getId().longValue(), 1000L);
		Assert.assertEquals(response.getSecondName(), "Alighieri");
		Assert.assertTrue(response.getAddresses().size() == 1);
		Assert.assertTrue(response.getPhoneNumbers().size() == 1);
	}
	
	@Test
	@Order(3)
	public void updateContact() throws Exception{
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// Create contact object
		ContactBaseTO contact = new ContactTO();
		contact.setFirstName("Giovanni");
		contact.setSecondName("Boccaccio");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		contact.setDateOfBirth(formatter.parse("24-10-1978"));
		
		HttpEntity<ContactBaseTO> entity = new HttpEntity<>(contact, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<ContactBaseTO> response = 
								restTemplate.exchange(
										createURLWithPort("/api/v1/contacts/2"), 
										HttpMethod.PUT, 
										entity, 
										ContactBaseTO.class);
		
		Assert.assertNotEquals(response.getBody().getFirstName(), "Dante");
		Assert.assertEquals(response.getBody().getSecondName(), "Boccaccio");
		
	}
	
	@Test
	@Order(4)
	public void deleteContact() throws Exception{
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.delete(createURLWithPort("/api/v1/contacts/2"));
		
		ResponseEntity<ContactTO> responseGet = 
				restTemplate.exchange(
						createURLWithPort("/api/v1/contacts/2"), 
						HttpMethod.GET, 
						entity, 
						ContactTO.class);
		
		Assert.assertEquals(responseGet.getStatusCode(), HttpStatus.OK);
		Assert.assertTrue(responseGet.getBody() == null);
	}
	
	@Test
	@Order(5)
	public void selectContactByCriteria() throws Exception{
				
		// Create object
		ContactCriteria contact = new ContactCriteria();
		contact.setFirstName("Gianpaolo");
		contact.setSecondName("Alta");
		contact.setAgeFrom(10);
		contact.setAgeTo(40);
		contact.setAddressCity("Teramo");
		// contact.setAddressStreet("Rodo");
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<ContactCriteria> entity = new HttpEntity<>(contact, headers);

		RestTemplate restTemplate = new RestTemplate();

		ContactTO[] response = restTemplate.postForObject(createURLWithPort("/api/v1/contacts/searches"), 
						entity, 
						ContactTO[].class);
				
		Assert.assertEquals(response[0].getFirstName(), "Gianpaolo");
		Assert.assertTrue(response.length == 1);
	}
	
	
	// ADDRESSES TEST
	@Test
	@Order(6)
	public void selectAllAddress() throws Exception{
			
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<AddressTO[]> response = restTemplate.exchange(
				createURLWithPort("/api/v1/contacts/1/addresses"),
				HttpMethod.GET, entity, AddressTO[].class);
		
		Assert.assertTrue(response.getBody().length == 1);
		
		Assert.assertEquals(response.getBody()[0].getCity(), "Teramo");
	}
	
	@Test
	@Order(7)
	public void insertAddress() throws Exception{
				
		// Create object
		AddressTO address = new AddressTO();
		address.setCity("Firenze");
		address.setPostalCode("50100");
		address.setStreet("Via Divina Commedia");
		address.setStreetNumber("1");
				
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AddressTO> entity = new HttpEntity<>(address, headers);

		RestTemplate restTemplate = new RestTemplate();

		AddressTO response = restTemplate.postForObject(createURLWithPort("/api/v1/contacts/1/addresses"), 
						entity, 
						AddressTO.class);
				
		Assert.assertEquals(response.getCity(), "Firenze");	
	}
	
	@Test
	@Order(8)
	public void updateAddress() throws Exception{
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		// Create object
		AddressTO address = new AddressTO();
		address.setCity("Nocera Superiore");
		address.setPostalCode("84015");
		address.setStreet("Via Divina Commedia");
		address.setStreetNumber("1");
		
		HttpEntity<AddressTO> entity = new HttpEntity<>(address, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<AddressTO> response = 
								restTemplate.exchange(
										createURLWithPort("/api/v1/contacts/1/addresses/3"), 
										HttpMethod.PUT, 
										entity, 
										AddressTO.class);
		
		Assert.assertNotEquals(response.getBody().getCity(), "Firenze");
		Assert.assertEquals(response.getBody().getPostalCode(), "84015");
		
	}
	
	@Test
	@Order(9)
	public void deleteAddress() throws Exception{
		
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		
		restTemplate.delete(createURLWithPort("/api/v1/contacts/1/addresses/3"));
		
		ResponseEntity<AddressTO> responseGet = 
				restTemplate.exchange(
						createURLWithPort("/api/v1/contacts/1/addresses/3"), 
						HttpMethod.GET, 
						entity, 
						AddressTO.class);
		
		Assert.assertEquals(responseGet.getStatusCode(), HttpStatus.OK);
		Assert.assertTrue(responseGet.getBody() == null);
	}
	
	// PHONE NUMBERS TEST
		@Test
		@Order(10)
		public void selectAllPhoneNumbers() throws Exception{
				
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<PhoneNumberTO[]> response = restTemplate.exchange(
					createURLWithPort("/api/v1/contacts/1/phone-numbers"),
					HttpMethod.GET, entity, PhoneNumberTO[].class);
			
			Assert.assertTrue(response.getBody().length == 1);
			
			Assert.assertEquals(response.getBody()[0].getNumber(), "+393206123580");
		}
		
		@Test
		@Order(11)
		public void insertPhoneNumber() throws Exception{
					
			// Create  object
			PhoneNumberTO phoneNumberTO = new PhoneNumberTO();
			phoneNumberTO.setType("prova");
			phoneNumberTO.setNumber("123456");
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<PhoneNumberTO> entity = new HttpEntity<>(phoneNumberTO, headers);

			RestTemplate restTemplate = new RestTemplate();

			PhoneNumberTO response = restTemplate.postForObject(createURLWithPort("/api/v1/contacts/1/phone-numbers"), 
							entity, 
							PhoneNumberTO.class);
					
			Assert.assertEquals(response.getType(), "prova");	
		}
		
		@Test
		@Order(12)
		public void updatePhoneNumber() throws Exception{
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			// Create  object
			PhoneNumberTO phoneNumberTO = new PhoneNumberTO();
			phoneNumberTO.setType("prova2");
			phoneNumberTO.setNumber("78910");
			
			HttpEntity<PhoneNumberTO> entity = new HttpEntity<>(phoneNumberTO, headers);

			RestTemplate restTemplate = new RestTemplate();
			
			ResponseEntity<PhoneNumberTO> response = 
									restTemplate.exchange(
											createURLWithPort("/api/v1/contacts/1/phone-numbers/3"), 
											HttpMethod.PUT, 
											entity, 
											PhoneNumberTO.class);
			
			Assert.assertNotEquals(response.getBody().getType(), "prova");
			Assert.assertEquals(response.getBody().getNumber(), "78910");
			
		}
		
		@Test
		@Order(13)
		public void deletePhoneNumber() throws Exception{
			
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<>(null, headers);

			RestTemplate restTemplate = new RestTemplate();
			
			restTemplate.delete(createURLWithPort("/api/v1/contacts/1/phone-numbers/3"));
			
			ResponseEntity<PhoneNumberTO> responseGet = 
					restTemplate.exchange(
							createURLWithPort("/api/v1/contacts/1/phone-numbers/3"), 
							HttpMethod.GET, 
							entity, 
							PhoneNumberTO.class);
			
			Assert.assertEquals(responseGet.getStatusCode(), HttpStatus.OK);
			Assert.assertTrue(responseGet.getBody() == null);
		}
	
	
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}

