package ch.bestvision.abcbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bestvision.abcbank.entity.persistence.PhoneNumber;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {
	List<PhoneNumber> findByContactId(Long id);
	
	PhoneNumber findByIdAndContactId(Long id, Long contactId);
}
