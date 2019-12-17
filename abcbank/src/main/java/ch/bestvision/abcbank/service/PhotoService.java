package ch.bestvision.abcbank.service;

import java.io.IOException;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ch.bestvision.abcbank.entity.PhotoTO;
import ch.bestvision.abcbank.entity.persistence.Contact;
import ch.bestvision.abcbank.entity.persistence.Photo;
import ch.bestvision.abcbank.exceptions.UploadException;
import ch.bestvision.abcbank.repository.ContactRepository;
import ch.bestvision.abcbank.repository.PhotoRepository;
import ch.bestvision.abcbank.utils.Constants;

@Service
public class PhotoService {
	
	@Autowired
	PhotoRepository photoRepository;
	
	@Autowired
	ContactRepository contactRepository;
	
	@Autowired
	ModelMapper modelMapper;

	public PhotoTO create(Long contactId, MultipartFile file) {
		
		try {
			
			Photo photo = convertToEntity(file);
		
			photo.setContact(contactRepository.getOne(contactId));
			
			return convertToTO(photoRepository.save(photo));
		} catch(IOException e) {
			throw new UploadException("Error during upload file: " + file.getName());
		}
	}
	
	public void delete(Long contactId) {
		Photo photoToDelete = photoRepository.findByContactId(contactId);
		
		if(photoToDelete == null) {
			throw new EntityNotFoundException(String.format(Constants.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE,PhotoTO.class, contactId));
		}
		
		photoRepository.delete(photoToDelete);
	}
	
	public PhotoTO selectAll(Long contactId) {
		return convertToTO(photoRepository.findByContactId(contactId));
	}
	
	
	private PhotoTO convertToTO(Photo photo) {
		if (photo == null) {
			return null;
		}
		return modelMapper.map(photo, PhotoTO.class);
	}
	
	private Photo convertToEntity(MultipartFile file) throws IOException {
		Photo photo = new Photo();
		
		// Normalize file name
		photo.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
		photo.setFileContentType(file.getContentType());
		photo.setFileData(file.getBytes());
		
		return photo;
	}
}
