package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class Payments {
	private int paymentid;
	private Date monthyear;
	private int  budgetamount;
	private int totalpayment;
	private BigDecimal averagepayment;
	private  String formattedmonthyear;
}
