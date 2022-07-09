package com.sist.nono.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sist.nono.adapter.GsonLocalDateTimeAdapter;
import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;
import com.sist.nono.model.User;
import com.sist.nono.service.BoardCommentService;
import com.sist.nono.service.BoardService;
import com.sist.nono.service.UserService;

@RestController
@RequestMapping("/boardComment")
public class BoardCommentController {
	
	
	@Autowired
	private BoardCommentService commentService;
	
	@GetMapping("/list/{b_no}")
	public List<BoardComment> list(@PathVariable int b_no){
		return commentService.findAll(b_no);
	}
	
	@PostMapping("/save/{b_no}")
	public String save(String bc_content, @PathVariable int b_no) {
		commentService.save(bc_content, b_no);
		return "등록성공";
	}
	@PutMapping("/update")
	public int update(@RequestBody BoardComment comment) {
		return commentService.update(comment);
	}
	
	@DeleteMapping("/delete/{bc_no}")
	public String delete(@PathVariable int bc_no) {
		commentService.delete(bc_no); 
		return "삭제성공";
	}
	
}