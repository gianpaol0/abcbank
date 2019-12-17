package ch.bestvision.abcbank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bestvision.abcbank.entity.persistence.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByContactId(Long id);
	
	Address findByIdAndContactId(Long id, Long contactId);
}
