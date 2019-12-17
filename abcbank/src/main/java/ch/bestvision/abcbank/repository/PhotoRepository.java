package ch.bestvision.abcbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.bestvision.abcbank.entity.persistence.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
	Photo findByContactId(Long id);

}
