package ch.bestvision.abcbank.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.bestvision.abcbank.entity.AddressTO;
import ch.bestvision.abcbank.entity.persistence.Address;
import ch.bestvision.abcbank.entity.persistence.Contact;
import ch.bestvision.abcbank.repository.AddressRepository;
import ch.bestvision.abcbank.repository.ContactRepository;
import ch.bestvision.abcbank.utils.Constants;

@Service
public class AddressService {
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ModelMapper modelMapper;

	
	public AddressTO create(Long contactId, AddressTO addressTO) {
		
		Address address = convertToEntity(addressTO);
		address.setId(null);
		
		address.setContact(contactRepository.getOne(contactId));
		
		return convertToTO(addressRepository.save(address));
	}
	
	public AddressTO update(Long contactId, Long id, AddressTO address) {
		Address addressToUpdate = addressRepository.getOne(id);
		checkContactId(addressToUpdate, contactId);
		// overwrite properties of addressToUpdate but ignore id property
		BeanUtils.copyProperties(address, addressToUpdate, "id");
		
		return convertToTO(addressRepository.save(addressToUpdate));
	}
	
	public void delete(Long contactId, Long id) {
		Address addressToDelete = addressRepository.getOne(id);
		checkContactId(addressToDelete, contactId);
		addressRepository.delete(addressToDelete);
	}
		
	public AddressTO selectById(Long id, Long contactId) {
		return convertToTO(addressRepository.findByIdAndContactId(id, contactId));		
	}
	
	public List<AddressTO> selectAll(Long contactId) {
		List<Address> addresses = addressRepository.findByContactId(contactId);
		return addresses.stream()
				.map(this::convertToTO)
				.collect(Collectors.toList());
	}
	
	private void checkContactId(Address address, Long contactId) {
		if (!address.getContact().getId().equals(contactId)) {
			throw new EntityNotFoundException(String.format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE,Contact.class, contactId));
		}
	}
	
	private AddressTO convertToTO(Address address) {
		if (address == null) {
			return null;
		}
		return modelMapper.map(address, AddressTO.class);
	}
	
	private Address convertToEntity(AddressTO addressTO) {
		if (addressTO == null) {
			return null;
		}
		return modelMapper.map(addressTO, Address.class);
	}
}
