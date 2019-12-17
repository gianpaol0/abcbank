package ch.bestvision.abcbank.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import ch.bestvision.abcbank.entity.ContactCriteria;
import ch.bestvision.abcbank.entity.persistence.Contact;
import ch.bestvision.abcbank.entity.persistence.Address;

public class ContactSpecification {
	
	public static Specification<Contact> firstNameContains(String firstName) {
        return (contact, cq, cb) -> cb.like(cb.lower(contact.get("firstName")), contains(firstName));
	}
	
	public static Specification<Contact> secondNameContains(String secondName) {
        return (contact, cq, cb) -> cb.like(cb.lower(contact.get("secondName")), contains(secondName));
	}
	
	public static Specification<Contact> ageRange(Integer ageFrom, Integer ageTo) {
		LocalDate now = LocalDate.now();
		// now.minusDays((365*ageTo)+364) because i am X year old from myBirthdayDate to myBirthdayDate + 364 days 
        return (contact, cq, cb) -> cb.between(contact.get("dateOfBirth"), toDate(now.minusDays((365*ageTo)+364)), toDate(now.minusYears(ageFrom)));
	}
	
	public static Specification<Contact> hasAddress(String city, String street) {
		return (contact, cq, cb) ->{
	        final Join<Contact, Address> addresses = contact.join("addresses", JoinType.LEFT);
	        return cb.and(
	        		cb.like(cb.lower(addresses.get("city")), contains(city)),
	        		cb.like(cb.lower(addresses.get("street")), contains(street))
	        		);
		};
			
	}
	
	
	public static Specification<Contact> criteriaSpecification(ContactCriteria criteria) {
		
		if (criteria == null)
			return null;
		
		Specification<Contact> spec = Specification.where(firstNameContains(criteria.getFirstName() == null ? "" : criteria.getFirstName()))
					.and(secondNameContains(criteria.getSecondName() == null ? "" : criteria.getSecondName()));
		
		if(StringUtils.hasText(criteria.getAddressCity()) 
				|| StringUtils.hasText(criteria.getAddressStreet())) {
			spec = spec.and(hasAddress(criteria.getAddressCity(), criteria.getAddressStreet()));
		}
		
		if( criteria.getAgeFrom()!=null || criteria.getAgeTo() != null) {
			spec = spec.and(ageRange(criteria.getAgeFrom() == null ? 0 : criteria.getAgeFrom(), 
									 criteria.getAgeTo() == null ? 150 : criteria.getAgeTo()));	
		}
		
		return spec;
	}
	
	private static String contains(String text) {
		if(text == null)
			return "%";
		return "%" + text.toLowerCase() + "%";
	}
	
	private static Date toDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
}
