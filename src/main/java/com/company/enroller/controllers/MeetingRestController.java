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
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/meetings") 
public class MeetingRestController {

	@Autowired // wstrzyknij instancje ktora znajdziesz
	MeetingService meetingService; // nie inicjalizuje

	@Autowired 
	ParticipantService participantService; 
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) 
	public ResponseEntity<?> getMeeting(@PathVariable("id") String id) {
		Meeting meeting = meetingService.findById(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
		meetingService.addMeeting(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.CREATED); 

	}

/*	@RequestMapping(value = "/{id}/participants", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipantToMeeting(@PathVariable("id") String id,
			@RequestBody Participant participant) { 
		
		Meeting meeting = meetingService.findById(id);
		if (meeting == null){
			return new ResponseEntity<>("meeting with id=" + id + " does not exist", HttpStatus.NOT_FOUND);
		}
		
		Collection<Participant> meetingParticipants = meeting.getParticipants();
		boolean participateInMeeting=false;
		
		for (Participant meetingParticipant : meetingParticipants) {
			if (meetingParticipant.getLogin().equals(participant.getLogin())) {
				participateInMeeting=true;
				break;
			}
		}
		if(!participantService.findByLogin(participant.getLogin()).getPassword().equals(participant.getPassword())) {
			return new ResponseEntity<>("wrong participant data", HttpStatus.BAD_REQUEST);
		}else if(participateInMeeting) {
			return new ResponseEntity<>("Participant already belongs to the meeting", HttpStatus.CONFLICT);
		}else{
			meeting.addParticipant(participant);
			meetingService.updateMeeting(meeting);
			return new ResponseEntity<>(HttpStatus.OK); 
		}
	}

	@RequestMapping(value = "/{id}/participants", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipantsOfMeeting(@PathVariable("id") String id) {

		Meeting meeting = meetingService.findById(id);
		Collection<Participant> participants = meeting.getParticipants();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") String id) {
		if (meetingService.findById(id) == null) {
			return new ResponseEntity<>("Unable to delete. Meeting " + id + " does not exist.", HttpStatus.NOT_FOUND);
		} else {
			Meeting meeting = meetingService.findById(id);
			meetingService.delete(meeting);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@RequestMapping(value = "/{id}/participants/{participantId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipantFromMeeting(@PathVariable("id") String id,
			@PathVariable("participantId") String participantLogin) {
		Meeting meeting = meetingService.findById(id);
		Participant participant = participantService.findByLogin(participantLogin);
		if ( participant== null ) {
			return new ResponseEntity<>("Unable to delete. Participant " + participantLogin + " does not belong to the meeting.", HttpStatus.NOT_FOUND);
		} else {
			meeting.removeParticipant(participant);
			meetingService.updateMeeting(meeting);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}
*/
}
