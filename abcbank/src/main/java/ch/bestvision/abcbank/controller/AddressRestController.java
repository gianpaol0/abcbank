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

import ch.bestvision.abcbank.entity.AddressTO;
import ch.bestvision.abcbank.service.AddressService;

@RestController
@RequestMapping("/api/v1/contacts")
public class AddressRestController {
	
	@Autowired
	AddressService addressService;
	
	@GetMapping("{contactId}/addresses")
	public List<AddressTO> selectAll(@PathVariable(value="contactId") Long contactId){
		return addressService.selectAll(contactId);
	}
	
	@PostMapping("{contactId}/addresses")
	public AddressTO create(@PathVariable(value="contactId") Long contactId, @RequestBody @Valid AddressTO address){
		return addressService.create(contactId, address);
	}
	
	@PutMapping("{contactId}/addresses/{id}")
	public AddressTO update(	@PathVariable(value="contactId") Long contactId, 
							@PathVariable(value="id") Long id, 
							@RequestBody @Valid AddressTO address){
		return addressService.update(contactId, id, address);
	}
	
	@DeleteMapping("{contactId}/addresses/{id}")
	public ResponseEntity<Void> delete(	@PathVariable(value="contactId") Long contactId, 
										@PathVariable(value="id") Long id){
		addressService.delete(contactId, id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("{contactId}/addresses/{id}")
	public AddressTO selectById(	@PathVariable(value="contactId") Long contactId, 
							 	@PathVariable(value = "id") Long id){
		return addressService.selectById(contactId, id);
	}	
	
	
	
}
