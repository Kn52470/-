package com.example.demo.repository;

import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {

	//ログインユーザー取得
			public User findByUser(String accountname);
			
	//ユーザ登録
			public int insertUser(User user);
	
	//パスワード更新
			public void updatePass(String accountname, String password ,Date birthday);
}
