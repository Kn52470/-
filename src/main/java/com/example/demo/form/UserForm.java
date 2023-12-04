package com.example.demo.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserForm {
	
	private int id;
	
	@NotEmpty
	private String accountname;
	
	@NotEmpty
	private String password;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@NotNull
	private Date birthday;
}
