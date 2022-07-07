package com.sist.nono.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.sist.nono.model.Board;
import com.sist.nono.repository.BoardRepository;

@RestController
@RequestMapping(("/api"))
class APIBoardController {

	@Autowired
	private BoardRepository repository;

	@GetMapping("/pageable")
	Page<Board> pageableTest(@PageableDefault(page = 0,size = 5) Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	
	
	// Aggregate root
	// tag::get-aggregate-root[]
	@GetMapping("/boards")
	List<Board> all() {
		return repository.findAll();
	}
	
	// end::get-aggregate-root[]

	@PostMapping("/boards")
	Board newBoard(@RequestBody Board newBoard) {
		return repository.save(newBoard);
	}

	// Single item

	@GetMapping("/boards/{b_no}")
	Board one(@PathVariable int b_no) {

		return repository.findById(b_no).get();
	}

	@PutMapping("/boards/{id}")
	Board replaceBoard(@RequestBody Board newBoard, @PathVariable int b_no) {

		return repository.findById(b_no)
				.map(board -> {
					board.setB_title(newBoard.getB_title());
					board.setB_content(newBoard.getB_content());
					return repository.save(board);
				})
				.orElseGet(() -> {
					newBoard.setB_no(b_no);
					return repository.save(newBoard);
				});
	}

	@DeleteMapping("/boards/{b_no}")
	void deleteBoard(@PathVariable int b_no) {
		repository.deleteById(b_no);
	}
}
