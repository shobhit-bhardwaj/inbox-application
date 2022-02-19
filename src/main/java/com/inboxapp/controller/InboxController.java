package com.inboxapp.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.inboxapp.entity.EmailList;
import com.inboxapp.entity.Folder;
import com.inboxapp.service.InboxService;

@Controller
public class InboxController {

	@Autowired
	private InboxService inboxService;

	@Autowired
	private PrettyTime prettyTime;

	@GetMapping ("/")
	public String home(@AuthenticationPrincipal OAuth2User user, @RequestParam(required = false) String folder, Model model) {
		if(user == null || !StringUtils.hasText(user.getAttribute("login"))) {
			return "index";
		}

		model.addAttribute("userName", user.getAttribute("login"));

		String userId = user.getAttribute("login");
		List<Folder> defaultFolders = inboxService.getDefaultFolders(userId);
		List<Folder> userFolders = inboxService.getUserFolders(userId);

		model.addAttribute("defaultFolders", defaultFolders);
		model.addAttribute("userFolders", userFolders);

		if(!StringUtils.hasText(folder))
			folder = "Inbox";

		List<EmailList> emailList = inboxService.getEmailByUserLabel(userId, folder);
		emailList.stream()
			.forEach(email -> {
				UUID time = email.getKey().getTimeUUID();
				String prettyAgoTime = prettyTime.format(new Date(Uuids.unixTimestamp(time)));
				email.setPrettyAgoTime(prettyAgoTime);
			});

		model.addAttribute("folder", folder);
		model.addAttribute("emailList", emailList);

		return "inbox-page";
	}
}