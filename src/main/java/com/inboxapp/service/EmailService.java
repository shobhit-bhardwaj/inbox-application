package com.inboxapp.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inboxapp.dao.EmailDao;
import com.inboxapp.entity.Email;

@Service
public class EmailService {

	@Autowired
	private EmailDao emailDao;

	public Optional<Email> getEmailById(UUID emailId) {
		return emailDao.findByEmailId(emailId);
	}
}