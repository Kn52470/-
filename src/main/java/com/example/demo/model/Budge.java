package com.example.demo.model;

import java.util.Date;

import lombok.Data;

@Data
public class Budge {

private int itemid;
	private String itemname;
	private int type;
	private int format;
	private Date registrationdate;
	private String text;
	private String money;
}
