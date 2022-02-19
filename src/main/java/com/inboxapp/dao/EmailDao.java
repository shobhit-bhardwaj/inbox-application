package com.inboxapp.dao;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.inboxapp.entity.Email;

@Repository
public interface EmailDao extends CassandraRepository<Email, UUID> {

	public Optional<Email> findByEmailId(UUID emailId);
}