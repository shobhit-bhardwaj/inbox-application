package com.inboxapp;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.inboxapp.config.DataStaxAstraConfig;
import com.inboxapp.dao.EmailDao;
import com.inboxapp.dao.EmailListDao;
import com.inboxapp.dao.FolderDao;
import com.inboxapp.entity.Email;
import com.inboxapp.entity.EmailList;
import com.inboxapp.entity.EmailListKey;
import com.inboxapp.entity.Folder;

@SpringBootApplication
public class InboxApplication {

	@Autowired
	private FolderDao folderDao;

	@Autowired
	private EmailListDao emailListDao;

	@Autowired
	private EmailDao emailDao;

	//@PostConstruct
	public void saveFolders() {
		List<Folder> folders = Arrays.asList(
				new Folder("shobhit-bhardwaj", "Official", "orange"),
				new Folder("shobhit-bhardwaj", "Travel", "cyan")
		);
		folderDao.saveAll(folders);

		EmailListKey key = null;
		EmailList emailList = null;
		Email email = null;

		for(int i=1; i<=10; i++) {
			key = new EmailListKey("shobhit-bhardwaj", "Inbox", Uuids.timeBased());
			emailList = new EmailList(key, Arrays.asList("shobhit-bhardwaj"), "Subject - " + i, true, null);
			emailListDao.save(emailList);

			email = new Email(key.getTimeUUID(), "shobhit-bhardwaj", emailList.getTo(), emailList.getSubject(), "This is the body of Message " + i);
			emailDao.save(email);
		}
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraConfig config) {
		Path bundle = config.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

	@Bean
	public PrettyTime prettyTime() {
		return new PrettyTime();
	}

	public static void main(String[] args) {
		SpringApplication.run(InboxApplication.class, args);
	}
}