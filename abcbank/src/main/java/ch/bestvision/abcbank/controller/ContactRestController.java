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

import ch.bestvision.abcbank.entity.ContactBaseTO;
import ch.bestvision.abcbank.entity.ContactCriteria;
import ch.bestvision.abcbank.entity.ContactTO;
import ch.bestvision.abcbank.service.ContactService;

@RestController
@RequestMapping("/api/v1/")
public class ContactRestController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/contacts")
	public List<ContactTO> selectAll(){
		return contactService.selectAll();
	}
	
	@PostMapping("/contacts")
	public ContactTO create(@RequestBody @Valid ContactTO contact){
		return contactService.create(contact);
	}
	
	@PutMapping("/contacts/{id}")
	public ContactTO update(@PathVariable(value="id") Long id,@RequestBody @Valid ContactBaseTO contactBase){
		return contactService.update(id, contactBase);
	}
	
	@DeleteMapping("/contacts/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value="id") Long id){
		contactService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/contacts/{id}")
	public ContactTO selectById(@PathVariable(value = "id") Long id){
		return contactService.selectById(id);
	}	
	
	@PostMapping("/contacts/searches")
	public List<ContactTO> selectByCriteria(@RequestBody ContactCriteria criteria){
		return contactService.selectByCriteria(criteria);
	}	
	
}
