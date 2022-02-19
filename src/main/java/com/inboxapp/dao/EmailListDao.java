package com.inboxapp.dao;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inboxapp.entity.EmailList;
import com.inboxapp.entity.EmailListKey;

@Repository
public interface EmailListDao extends CassandraRepository<EmailList, EmailListKey> {

	public List<EmailList> findByKey_UserIdAndKey_Label(String userId, String label);
}