package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants") //lokalizacja zasobow, tu dajemy endpointy
public class ParticipantRestController {

	@Autowired //wstrzyknij instancje ktora znajdziesz
	ParticipantService participantService; //nie inicjalizuje

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)  //RM - metoda obslugujaca zadanie
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login){
		Participant participant = participantService.findByLogin(login);
		if (participant==null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}
	
	 @RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> registerParticipant(@RequestBody Participant participant){ //dane w formaccie JSON jako body
		 // sprawdzic czy istnieje
		 if (participantService.findByLogin(participant.getLogin())!=null) {
			 return new ResponseEntity<>("Unable to create. Login "+ participant.getLogin()+" exist.", HttpStatus.CONFLICT);
		 }else{//zapisac
			 participantService.create(participant);
			 return new ResponseEntity<>(participant, HttpStatus.CREATED); //zwrocic
		 }		 
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login){ 
		 if (participantService.findByLogin(login)==null) {
			 return new ResponseEntity<>("Unable to delete. Login "+ login+"does not exist.", HttpStatus.NOT_FOUND);
		 }else{
			 Participant participant = participantService.findByLogin(login);
			 participantService.delete(participant);
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant){ //dane w formaccie JSON jako body
		 // sprawdzic czy istnieje
		 if ((participantService.findByLogin(participant.getLogin()))==null) {
			 participantService.create(participant);
			 return new ResponseEntity<>("Login "+ participant.getLogin()+" did not exist. it was created", HttpStatus.CREATED);
		 }else{//zapisac
			 if (!participant.getLogin().equals(login)) {
				 return new ResponseEntity<>("you try to change different user "+ participant.getLogin(), HttpStatus.CONFLICT);	 
			 }
			 participantService.update(login, participant);
			 return new ResponseEntity<>(participant, HttpStatus.OK); //zwrocic
		 }		 
	 }
}
