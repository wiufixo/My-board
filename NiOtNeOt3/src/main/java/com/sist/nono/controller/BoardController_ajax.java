//package com.sist.nono.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sist.nono.exception.CustomException;
//import com.sist.nono.exception.ErrorCode;
//import com.sist.nono.model.Board;
//import com.sist.nono.model.User;
//import com.sist.nono.service.BoardCommentService;
//import com.sist.nono.service.BoardService;
//import com.sist.nono.service.UserService;
//import com.sist.nono.validator.BoardValidator;
//
//@Controller
//@RequestMapping("/board")
//public class BoardController_ajax {
//
//	@Autowired
//	private BoardService boardService;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private BoardCommentService commentService;
//	
//	@Autowired
//	private BoardValidator boardValidator;
//	
//	@ResponseBody
//	@DeleteMapping("/delete/{b_no}")
//	public String delete(@PathVariable int b_no) {
//		boardService.delete(b_no);
//		return "delete OK";
//	}
//	
//	
//	@GetMapping("/detail/{b_no}")
//	public String detail(Model model, @PathVariable int b_no) {
//		model.addAttribute("board", boardService.getBoard(b_no));
//		model.addAttribute("comments", commentService.findAll(b_no));
//		return "board/detail";
//	}
//	
//	@GetMapping("/form")
//	public String form(Model model, @RequestParam(defaultValue = "0") int b_no) {
//		if(b_no==0) {
//			model.addAttribute("board", new Board());
//			
//		}else {
//			model.addAttribute("board", boardService.getBoard(b_no));
//		}
//		return "board/form";
//	}
//	
//	@PostMapping("/form")
//	public String formSubmit(Board board, BindingResult bindingResult) {
//		
//		boardValidator.validate(board, bindingResult); //validator 객체로 유효성 검사
//		if(bindingResult.hasErrors()) {
//			return "board/form";
//		}
//		
//		System.out.println(board);
//		
//		if(board.getB_no() != 0) {
//			boardService.update(board);
//		}else {
//			board.setUser(userService.findById(2));
//			boardService.save(board);
//		}
//		return "redirect:/board/list";
//	}
//	
//	@ResponseBody
//	@GetMapping("/boards")
//	public List<Board> listBoards(Model model, @PageableDefault(page = 0,size = 20) Pageable pageable) {
//		Page<Board> p = boardService.findAll(pageable);
//		return p.getContent();
//	}
//	
//	@GetMapping("/list")
//	public String list(Model model, @PageableDefault(page = 0,size = 20) Pageable pageable) {
//		Page<Board> p = boardService.findAll(pageable);
//		model.addAttribute("p",p);
//		return "board/list";
//	}
//	
//	
//}
