package ch.bestvision.abcbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.bestvision.abcbank.entity.PhotoTO;
import ch.bestvision.abcbank.service.PhotoService;

@RestController
@RequestMapping("/api/v1/contacts")
public class PhotoRestController {
	
	@Autowired
	PhotoService photoService;
	
	
	@GetMapping("{contactId}/photos")
	public ResponseEntity < Resource > selectAll(@PathVariable(value="contactId") Long contactId){
		PhotoTO photo = photoService.selectAll(contactId);
		
		if (photo == null) {
			return ResponseEntity.ok().body(null);
		}
		
		return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(new ByteArrayResource(photo.getFileData()));
	}
	
	@PostMapping(path = { "{contactId}/photos"})
	public PhotoTO create(	@PathVariable(value="contactId") Long contactId,
							@RequestParam("file") MultipartFile file){
		
		return photoService.create(contactId, file);
	}
	
	@DeleteMapping("{contactId}/photos")
	public ResponseEntity<Void> delete(	@PathVariable(value="contactId") Long contactId){
		photoService.delete(contactId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
	
	
}
