package com.example.demo.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class BudgeForm {

	private int itemid;
	
	@NotEmpty
	private String itemname;
	
	private int type;
	
	private int format;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date registrationdate;
	
	private String text;
	
	@NotEmpty
	private String money;
}
