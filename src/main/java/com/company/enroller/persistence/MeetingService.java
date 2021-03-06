package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

/*	public Collection<Meeting> getAll() {
		String hql = "IFROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}*/
	
	public Collection<Meeting> getAll() {
		return connector.getSession().createCriteria(Meeting.class).list();
	}

	public Meeting findById(String id) {
/*		String hql = "FROM Meeting M WHERE M.id="+id;
		Query query = connector.getSession().createQuery(hql);
		return (Meeting)query.list().get(0);*/
		Collection<Meeting> meetingsList = connector.getSession().createCriteria(Meeting.class).list();
		Meeting foundMeeting=null;
		for (Meeting meeting : meetingsList) {
			if (String.valueOf(meeting.getId()).equals(id)) {
				foundMeeting=meeting;
			}
		}
		return foundMeeting;
	}

	public void addMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void updateMeeting(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().update(meeting);
		transaction.commit();		
	}

	public void delete(Meeting meeting) {
		Transaction transaction = connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

}
