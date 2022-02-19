package com.inboxapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.inboxapp.entity.Email;
import com.inboxapp.entity.Folder;
import com.inboxapp.service.EmailService;
import com.inboxapp.service.InboxService;

@Controller
public class EmailController {

	@Autowired
	private EmailService emailService ;

	@Autowired
	private InboxService inboxService;

	@GetMapping ("/compose")
	public String getComposePage(@AuthenticationPrincipal OAuth2User user, @RequestParam (required=false) String to, Model model) {
		if(user == null || !StringUtils.hasText(user.getAttribute("login"))) {
			return "index";
		}

		String userId = user.getAttribute("login");
		List<Folder> defaultFolders = inboxService.getDefaultFolders(userId);
		List<Folder> userFolders = inboxService.getUserFolders(userId);

		model.addAttribute("defaultFolders", defaultFolders);
		model.addAttribute("userFolders", userFolders);

		if(!StringUtils.hasText(to)) {
			to = "";
		} else {
			to = Arrays.asList(to.split(",")).stream()
					.map(id -> id.trim())
					.distinct()
					.collect(Collectors.joining(", "));
		}
		model.addAttribute("to", to);

		return "compose-page";
	}

	@GetMapping ("/emails/{emailId}")
	public String getEmail(@AuthenticationPrincipal OAuth2User user, @PathVariable UUID emailId, Model model) {
		if(user == null || !StringUtils.hasText(user.getAttribute("login"))) {
			return "index";
		}

		Optional<Email> optionalEmail = emailService.getEmailById(emailId);
		if(optionalEmail.isEmpty()) {
			return "inbox-page";
		}

		String userId = user.getAttribute("login");
		List<Folder> defaultFolders = inboxService.getDefaultFolders(userId);
		List<Folder> userFolders = inboxService.getUserFolders(userId);

		model.addAttribute("defaultFolders", defaultFolders);
		model.addAttribute("userFolders", userFolders);

		Email email = optionalEmail.get();
		String toList = String.join(",", email.getTo());
		model.addAttribute("email", email);
		model.addAttribute("toList", toList);


		return "email-page";
	}
}