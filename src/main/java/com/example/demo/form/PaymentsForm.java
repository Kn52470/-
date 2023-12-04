package com.example.demo.form;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PaymentsForm {

	private int paymentid;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date monthyear;
	
	private int  budgetamount;
	
	private int totalpayment;
	
	private BigDecimal averagepayment;
	
	private  String formattedmonthyear;
}
