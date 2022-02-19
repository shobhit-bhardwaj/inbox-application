package com.inboxapp.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inboxapp.dao.EmailListDao;
import com.inboxapp.dao.FolderDao;
import com.inboxapp.entity.EmailList;
import com.inboxapp.entity.Folder;

@Service
public class InboxService {

	@Autowired
	private FolderDao folderDao;

	@Autowired
	private EmailListDao emailListDao;

	public List<Folder> getDefaultFolders(String userId) {
		return Arrays.asList(
				new Folder(userId, "Inbox", "blue"),
				new Folder(userId, "Sent Items", "green"),
				new Folder(userId, "Importent", "yellow")
		);
	}

	public List<Folder> getUserFolders(String userId) {
		return folderDao.findByUserId(userId);
	}

	public List<EmailList> getEmailByUserLabel(String userId, String label) {
		return emailListDao.findByKey_UserIdAndKey_Label(userId, label);
	}
}