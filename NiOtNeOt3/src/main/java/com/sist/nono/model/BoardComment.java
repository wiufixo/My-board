package com.sist.nono.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"user","board"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "boardComment")
@Entity
public class BoardComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bc_no;
	
	@ManyToOne
	@JoinColumn(name = "cu_no")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "b_no")
	@JsonIgnoreProperties({"boardComment","boardFile","user"})
	private Board board;
	
	@Column(nullable = false, length = 200)
	private String bc_content;
	
//	@Column(nullable = false)
//	private int bc_ref;
//	
//	@ColumnDefault("0")
//	private int bc_step;
//	
//	@ColumnDefault("0")
//	private int bc_level;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss") //ajax 날짜로 올때 변환식
	@CreationTimestamp
	private Timestamp bc_created;
	
	private Date bc_updated;
	
	
}
