package com.sist.nono.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.Board;
import com.sist.nono.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void delete(int b_no) {
		boardRepository.deleteById(b_no);
	}
	
	@Transactional
	public void update(Board r_board) {
		
		Board board = boardRepository.findById(r_board.getB_no())
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
				}); //영속화 완료
		board.setB_title(r_board.getB_title());
		board.setB_content(r_board.getB_content());
		
		boardRepository.update(board.getB_title(), board.getB_content(), board.getB_no());
	}
	
	@Transactional(readOnly = true)
	public Board getBoard(int b_no) {
		return boardRepository.findById(b_no).get();
	}
	
	@Transactional
	public void save(Board board) {
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> findAll(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
}
