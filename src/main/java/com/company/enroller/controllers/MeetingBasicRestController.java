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

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;

@RestController
@RequestMapping("/meetings") //lokalizacja zasobow, tu dajemy endpointy
public class MeetingBasicRestController {

	@Autowired //wstrzyknij instancje ktora znajdziesz
	MeetingService meetingService; //nie inicjalizuje

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)  //RM - metoda obslugujaca zadanie
	public ResponseEntity<?> getMeeting(@PathVariable("id") String id){
		Meeting meeting = meetingService.findById(id);
		if (meeting==null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
	
	 @RequestMapping(value = "", method = RequestMethod.POST)
	 public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting){ //dane w formaccie JSON jako body
		meetingService.addMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED); //zwrocic
		 	 
	 }
	 
	 @RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
	 public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") String id, @RequestBody Participant participant){ //dane w formaccie JSON jako body
			Meeting meeting = meetingService.findById(id);
			if (meeting!=null) {
				meeting.addParticipant(participant);
				meetingService.updateMeeting(meeting);
				return new ResponseEntity<>(HttpStatus.OK); //zwrocic
			}else {
				return new ResponseEntity<>("meeting with id="+id+" does not exist", HttpStatus.NOT_FOUND);
			} 			 
	 }
	 
	 @RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	 public ResponseEntity<?> getParticipantsOfMeeting(@PathVariable("id") String id){ 
 		
		Meeting meeting = meetingService.findById(id);
		Collection<Participant> participants = meeting.getParticipants();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	 }
	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 public ResponseEntity<?> deleteMeeting(@PathVariable("id") String id){ 
		 if (meetingService.findById(id)==null) {
			 return new ResponseEntity<>("Unable to delete. Meeting "+ id+" does not exist.", HttpStatus.NOT_FOUND);
		 }else{
			 Meeting meeting = meetingService.findById(id);
			 meetingService.delete(meeting);
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		 }
		
	 }
	 /*	 
	 @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	 public ResponseEntity<?> updateParticipant(@PathVariable("id") String login, @RequestBody Participant participant){ //dane w formaccie JSON jako body
		 // sprawdzic czy istnieje
		 if (participantService.findByLogin(participant.getLogin())==null) {
			 participantService.create(participant);
			 return new ResponseEntity<>("Login "+ participant.getLogin()+" did not exist. it was created", HttpStatus.NOT_FOUND);
		 }else{//zapisac
			 if (!participant.getLogin().equals(login)) {
				 return new ResponseEntity<>("you try to change different user "+ participant.getLogin(), HttpStatus.CONFLICT);	 
			 }
			 participantService.update(login, participant);
			 return new ResponseEntity<>(participant, HttpStatus.OK); //zwrocic
		 }		 
	 }*/
}
