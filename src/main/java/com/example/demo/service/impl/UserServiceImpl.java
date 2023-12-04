package com.example.demo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserMapper;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper UserMapper;
	
	
	//ログインユーザー情報取得
			@Override
			public User getLoginUser(String accountname) {
				
				return UserMapper.findByUser(accountname);
			}
			
	//アカウント登録
			@Override
			public int insertUser(User user) {
				
				
				return UserMapper.insertUser(user);
			}
			
	//パスワード更新
			@Override
			public void updatePass(String accountname, String password , Date birthday) {
				
				 UserMapper.updatePass(accountname, password, birthday);
				
			}
}
