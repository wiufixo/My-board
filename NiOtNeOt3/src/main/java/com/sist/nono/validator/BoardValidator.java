package com.sist.nono.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import com.sist.nono.model.Board;
import com.sist.nono.model.BoardComment;

@Component
public class BoardValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Board.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Board b = (Board) obj;
		if(StringUtils.isEmpty(b.getB_title())) {
			errors.rejectValue("b_title", "key", "제목 없음");
		}
		if(StringUtils.isEmpty(b.getB_content())) {
			errors.rejectValue("b_content", "key", "내용 없음");
		}
	}
}
