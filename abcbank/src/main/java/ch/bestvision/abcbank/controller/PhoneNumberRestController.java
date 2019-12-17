package ch.bestvision.abcbank.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.bestvision.abcbank.entity.PhoneNumberTO;
import ch.bestvision.abcbank.service.PhoneNumberService;

@RestController
@RequestMapping("/api/v1/contacts")
public class PhoneNumberRestController {
	
	@Autowired
	PhoneNumberService phoneNumberService;
	
	@GetMapping("{contactId}/phone-numbers")
	public List<PhoneNumberTO> selectAll(@PathVariable(value="contactId") Long contactId){
		return phoneNumberService.selectAll(contactId);
	}
	
	@PostMapping("{contactId}/phone-numbers")
	public PhoneNumberTO create(@PathVariable(value="contactId") Long contactId, 
								@RequestBody @Valid PhoneNumberTO phoneNumber){
		return phoneNumberService.create(contactId, phoneNumber);
	}
	
	@PutMapping("{contactId}/phone-numbers/{id}")
	public PhoneNumberTO update(@PathVariable(value="contactId") Long contactId, 
								@PathVariable(value="id") Long id, 
								@RequestBody @Valid PhoneNumberTO phoneNumber){
		return phoneNumberService.update(contactId, id, phoneNumber);
	}
	
	@DeleteMapping("{contactId}/phone-numbers/{id}")
	public ResponseEntity<Void> delete(	@PathVariable(value="contactId") Long contactId, 
										@PathVariable(value="id") Long id){
		phoneNumberService.delete(contactId, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("{contactId}/phone-numbers/{id}")
	public PhoneNumberTO selectById(	@PathVariable(value="contactId") Long contactId, 
							 	@PathVariable(value = "id") Long id){
		return phoneNumberService.selectById(contactId, id);
	}	
	
	
	
}
