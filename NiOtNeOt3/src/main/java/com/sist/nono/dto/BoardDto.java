package com.sist.nono.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardDto {
	private int b_no;
	private int cu_no;
	private String cu_id;
	private String b_title;
	private String b_content;
//	private int b_ref;
//	private int b_step;
//	private int b_level;
	private Date b_created;
	private Date b_update;
	private int b_hit;
	private int bc_no;
	private String bc_content;
}
