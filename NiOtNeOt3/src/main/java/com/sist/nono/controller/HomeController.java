package com.sist.nono.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sist.nono.auth.PrincipalDetail;
import com.sist.nono.model.User;
import com.sist.nono.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/account/login")
	public String login() {
		return "account/login";
	}

	@GetMapping("/account/join")
	public String join(Model model) {
		model.addAttribute("user", new User());
		return "account/join";
	}
	
	@PostMapping("/account/join")
	public String joinSubmit(@Valid User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.info(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return "/account/join";
		}
		userService.save(user);
		return "redirect:/";
	}
	
	@GetMapping("/account/update")
	public String update(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
		model.addAttribute("user", principal.getUser());
		return "account/update";
	}
	
	@PostMapping("/account/update")
	public String updateSubmit(@Valid User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			log.info(bindingResult.getAllErrors().get(0).getDefaultMessage());
			return "/account/update";
		}
		userService.update(user); // ???????????? ????????? DB??? ????????? ????????? session?????? ???????????? ?????? ????????? ????????? ?????? ?????? ?????????????????????
		//????????????
		Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(user.getCu_id(), user.getCu_pwd()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "redirect:/";
	}
	
//	//????????????
//	@GetMapping("/resign")
//	public String resign(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
//		model.addAttribute("principal", principal);
//		System.out.println(principal.getPassword());
//		System.out.println(principal.getUsername());
//		System.out.println(principal.getUser().getCu_id());
//		System.out.println(principal.getUser().getCu_pwd());
//		return "account/resign";
//	}
//	//????????????
//	@PostMapping("/user/delete")
//	public String delete(User user) {
//		userService.resign(user);
//		return "redirect:/";
//	}
}
