package com.example.demo.service;

import java.util.Date;

import com.example.demo.model.User;

public interface UserService {

	//ログインユーザー情報取得
			public User getLoginUser(String accountname);
	//アカウント登録
			public int insertUser(User user);
	//パスワード更新
			public void updatePass(String accountname, String password , Date birthday);
}
