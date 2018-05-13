package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(String id) {
		//return (Meeting) connector.getSession().get(Meeting.class, Integer.valueOf(id));
		String hql = "FROM Meeting m where m.id="+id;
		Query query = connector.getSession().createQuery(hql);
		return (Meeting)query.list().get(0);
	}

}
