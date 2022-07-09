package com.sist.nono.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.nono.dao.BoardDao;
import com.sist.nono.dto.BoardDto;
import com.sist.nono.exception.CustomException;
import com.sist.nono.exception.ErrorCode;
import com.sist.nono.model.Board;
import com.sist.nono.model.User;
import com.sist.nono.paging.CommonParams;
import com.sist.nono.paging.Pagination;
import com.sist.nono.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;


	@Autowired
	private BoardDao dao;

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

	@Transactional()
	public Board getBoard(int b_no) {
		Board board = boardRepository.findById(b_no).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
		board.increaseHit();
		return board;
	}

	@Transactional
	public void save(Board board) {
		boardRepository.save(board);
	}

	/**
	 * 게시글 리스트 조회 - (With. pagination information)
	 */
	public Map<String, Object> findAll(CommonParams params) {

		Map<String, Object> response = new HashMap<>();
		
		if(params.getSearchType() != null) {
			System.out.println("여기!!!!!!!!!");
			if(params.getSearchType().equals("bc")) {
				System.out.println("여기22");
				if (dao.countComment(params) < 1) {
					
					return Collections.emptyMap();
				}
				int count = dao.countComment(params);
				Pagination pagination = new Pagination(count, params);
				params.setPagination(pagination);
				List<BoardDto> list1 = dao.findComment(params);
				Map<Integer, BoardDto> map = new HashMap<Integer, BoardDto>();
				for(BoardDto b : list1) {
					map.put(b.getB_no(), b);
				}
				
				List<BoardDto> list = new ArrayList<>(map.values());
				
				response.put("params", params);
				response.put("list", list);
				return response;
			}
		}
		// 게시글 수 조회
		int count = dao.count(params);
		System.out.println("여긴안돼!!!!!!");
		// 등록된 게시글이 없는 경우, 로직 종료
		if (count < 1) {
			return Collections.emptyMap();
		}

		// 페이지네이션 정보 계산
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);

		// 게시글 리스트 조회
		List<BoardDto> list = dao.findAll(params);

		// 데이터 반환
		response.put("params", params);
		response.put("list", list);

		return response;

	}


	//	@Transactional(readOnly = true)
	//	public Page<Board> findAll2(Pageable pageable) {
	//		return boardRepository.findAll(pageable);
	//	}
}
