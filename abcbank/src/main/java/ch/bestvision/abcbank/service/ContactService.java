package ch.bestvision.abcbank.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.bestvision.abcbank.entity.ContactBaseTO;
import ch.bestvision.abcbank.entity.ContactCriteria;
import ch.bestvision.abcbank.entity.ContactTO;
import ch.bestvision.abcbank.entity.persistence.Contact;
import ch.bestvision.abcbank.repository.ContactRepository;
import ch.bestvision.abcbank.repository.ContactSpecification;
import ch.bestvision.abcbank.utils.Constants;

@Service
public class ContactService {

	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ContactTO create(ContactTO contactTO) {
		
		Contact contact = convertToEntity(contactTO);
		
		contact.setId(null);
		// set master object to all children
		if (contact.getAddresses() != null) {
			contact.getAddresses().
				forEach(address -> {
					address.setContact(contact); 
					address.setId(null);
				});
		}
		if (contact.getPhoneNumbers() != null) {
			contact.getPhoneNumbers().
				forEach(phoneNumber -> {
					phoneNumber.setContact(contact);
					phoneNumber.setId(null);
				});
		}
		
		return convertToTO(contactRepository.save(contact));
	}
	
	public ContactTO update(Long id, ContactBaseTO contact) {
		
		Contact contactToUpdate = getContactById(id);
		
		// overwrite contactToUpdate properties 
		BeanUtils.copyProperties(contact, contactToUpdate);
		
		return convertToTO(contactRepository.save(contactToUpdate));
		
	}
	
	public void delete(Long contactId) {
		contactRepository.deleteById(contactId);
	}
		
	public ContactTO selectById(Long contactId) {
		return convertToTO(contactRepository.findById(contactId).orElse(null));
	}
	
	public List<ContactTO> selectAll() {
		List<Contact> contacts = contactRepository.findAll();
		return contacts.stream()
				.map(this::convertToTO)
				.collect(Collectors.toList());
		
	}
	
	public List<ContactTO> selectByCriteria(ContactCriteria criteria) {
		
		List<Contact> contacts = contactRepository.findAll(ContactSpecification.criteriaSpecification(criteria));
		return contacts.stream()
				.map(this::convertToTO)
				.collect(Collectors.toList());
		
	}
	
	private ContactTO convertToTO(Contact contact) {
		if (contact == null) {
			return null;
		}
		return modelMapper.map(contact, ContactTO.class);
	}
	
	private Contact convertToEntity(ContactTO contactTO) {
		if (contactTO == null) {
			return null;
		}
		return modelMapper.map(contactTO, Contact.class);
	}
	
	private Contact getContactById(Long id) {
		 
		 if (contactRepository.existsById(id)) {
			 return contactRepository.getOne(id);
		 } 
		 // else
		 throw new EntityNotFoundException(String.format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE,Contact.class, id));
	}
}
