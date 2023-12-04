package com.example.demo.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.service.UserService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String accountname) throws UsernameNotFoundException {
		
		
		//ユーザー情報取得
		com.example.demo.model.User loginUser = userService.getLoginUser(accountname);
		
		//ユーザーが存在しない場合
		if(loginUser == null) {
			throw new UsernameNotFoundException("user not found");
		}
				
		//UeserDetails生成
		UserDetails userDetails = (UserDetails) new User(loginUser.getAccountname(),loginUser.getPassword(),Collections.emptyList());

			return userDetails;
	
	}
}