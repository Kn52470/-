package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private int id;
	private String accountname;
	private String password;
	private Date birthday;
}
