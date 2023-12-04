package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserForm;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AccountController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@GetMapping("/register")
	public String register(Model model, @ModelAttribute UserForm userForm) {
		
		model.addAttribute("accountForm", userForm);
		
		return "register";
	}

	@PostMapping("/register")
	public String account(@ModelAttribute @Validated UserForm userForm, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			// バリデーションエラーがある場合の処理
			return register(model, userForm);
		}
		
		log.info(userForm.toString());
		
		User user = modelMapper.map(userForm,User.class);

		String rawPassword = user.getPassword();
		user.setPassword(encoder.encode(rawPassword));
		
		//ユーザ登録
		userService.insertUser(user);
		
		return "redirect:/login";
	}
	
	@GetMapping("/updatePass")
	public String updatePass(Model model, @ModelAttribute UserForm userForm) {
		
		model.addAttribute("accountForm", userForm);
		
		return "updatePass";
	}
	
	@PostMapping("/updatePass")
	public String getPassword(@ModelAttribute @Validated UserForm userForm, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			// バリデーションエラーがある場合の処理
			return updatePass(model, userForm);
		}
		
		String rawPassword = userForm.getPassword();
		userForm.setPassword(encoder.encode(rawPassword));
		
		userService.updatePass(userForm.getAccountname(), userForm.getPassword(), userForm.getBirthday());
		
		return "redirect:/login";
	}

}

