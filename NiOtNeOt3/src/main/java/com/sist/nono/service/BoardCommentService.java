package com.sist.nono.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;
import com.sist.nono.model.User;
import com.sist.nono.repository.BoardCommentRepository;
import com.sist.nono.repository.BoardRepository;
import com.sist.nono.repository.UserRepository;

@Service
public class BoardCommentService {

	@Autowired
	private BoardCommentRepository boardCommentRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void delete(int bc_no) {
		boardCommentRepository.deleteById(bc_no);
	}
	
	@Transactional
	public void update(BoardComment r_boardComment) {
		
		/*
		 * BoardComment boardComment =
		 * boardCommentRepository.findById(r_boardComment.getBc_no()) .orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 찾기 실패: 글번호를 찾을 수 없습니다!"); }); //영속화
		 * 완료
		 */		
		boardCommentRepository.update(r_boardComment.getBc_content(), r_boardComment.getBc_no());
	}
	
	@Transactional(readOnly = true)
	public BoardComment getComment(int bc_no) {
		return boardCommentRepository.findById(bc_no).get();
	}
	
	@Transactional
	public void save(String bc_content, int b_no) {
		BoardComment boardComment = new BoardComment();
		boardComment.setBc_content(bc_content);
		User user = userRepository.findById(4).get();
		boardComment.setUser(user);
		Board board = boardRepository.findById(b_no).get();
		boardComment.setBoard(board);
		boardCommentRepository.save(boardComment);
	}
	
	@Transactional(readOnly = true)
	public List<BoardComment> findAll(int b_no) {
		return boardCommentRepository.findAllByNo(b_no);
	}
	
	@Transactional(readOnly = true)
	public int getTotByNo(int b_no) {
		return boardCommentRepository.getTotByNo(b_no);
	}
	
	
	
	
	
	
}
