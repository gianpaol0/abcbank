package ch.bestvision.abcbank.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.bestvision.abcbank.entity.PhoneNumberTO;
import ch.bestvision.abcbank.entity.persistence.Contact;
import ch.bestvision.abcbank.entity.persistence.PhoneNumber;
import ch.bestvision.abcbank.repository.ContactRepository;
import ch.bestvision.abcbank.repository.PhoneNumberRepository;
import ch.bestvision.abcbank.utils.Constants;

@Service
public class PhoneNumberService {
	@Autowired
	PhoneNumberRepository phoneNumberRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public PhoneNumberTO create(Long contactId, PhoneNumberTO phoneNumberTO) {
		
		PhoneNumber phoneNumber = convertToEntity(phoneNumberTO);
		phoneNumber.setId(null);
		phoneNumber.setContact(contactRepository.getOne(contactId));
		
		return convertToTO(phoneNumberRepository.save(phoneNumber));
	}
	
	public PhoneNumberTO update(Long contactId, Long id, PhoneNumberTO phoneNumber) {
		PhoneNumber phoneNumberToUpdate = phoneNumberRepository.getOne(id);
		checkContactId(phoneNumberToUpdate, contactId);
		// overwrite properties of addressToUpdate but ignore id property
		BeanUtils.copyProperties(phoneNumber, phoneNumberToUpdate, "id");
		
		return convertToTO(phoneNumberRepository.save(phoneNumberToUpdate));
	}
	
	public void delete(Long contactId, Long id) {
		PhoneNumber phoneNumberToDelete = phoneNumberRepository.getOne(id);
		checkContactId(phoneNumberToDelete, contactId);
		phoneNumberRepository.delete(phoneNumberToDelete);
	}
		
	public PhoneNumberTO selectById(Long id, Long contactId) {
		return convertToTO(phoneNumberRepository.findByIdAndContactId(id, contactId));		
	}
	
	public List<PhoneNumberTO> selectAll(Long contactId) {
		List<PhoneNumber> phoneNumbers = phoneNumberRepository.findByContactId(contactId);
		return phoneNumbers.stream()
				.map(this::convertToTO)
				.collect(Collectors.toList());
	}
	
	private void checkContactId(PhoneNumber phoneNumber, Long contactId) {
		
		if (!phoneNumber.getContact().getId().equals(contactId)) {
			throw new EntityNotFoundException(String.format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE,Contact.class, contactId));
		}
		
	}
	
	private PhoneNumberTO convertToTO(PhoneNumber phoneNumber) {
		if (phoneNumber == null) {
			return null;
		}
		return modelMapper.map(phoneNumber, PhoneNumberTO.class);
	}
	
	private PhoneNumber convertToEntity(PhoneNumberTO phoneNumberTO) {
		if (phoneNumberTO == null) {
			return null;
		}
		return modelMapper.map(phoneNumberTO, PhoneNumber.class);
	}
}
