package com.sist.nono.dto;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sist.nono.model.BoardFile;
import com.sist.nono.repository.BoardRepository;

import lombok.Getter;

@Component
@Getter
public class FileUploadDTO {
	
	@Autowired
	private BoardRepository boardRepository;
	
//	/** 오늘 날짜 */
//	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

	/** 업로드 경로 */
	private final String uploadPath = Paths.get("C:", "develop", "upload").toString();
//	private final String uploadPath = Paths.get("C:", "develop", "upload", today).toString();

	/**
	 * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	 * @return 랜덤 문자열
	 */
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
	 * @param files    - 파일 Array
	 * @param b_no - 게시글 번호
	 * @return 업로드 파일 목록
	 */
	public List<BoardFile> uploadFiles(List<MultipartFile> files, int b_no) {

		/* 파일이 비어있으면 비어있는 리스트 반환 */
//		for(MultipartFile f : files) {
//			if(f.getSize()<1) {
//				return Collections.emptyList();
//			}
//		}
//		if (files.get(0).getSize() < 1) {
//			System.out.println("비어있는파일리스트반환");
//			
//		}

		/* 업로드 파일 정보를 담을 비어있는 리스트 */
		List<BoardFile> fileList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		/* 파일 개수만큼 forEach 실행 */
		for (MultipartFile file : files) {
			try {
				
				if(file.getSize()>0) { //공파일은 제외
					/* 파일 확장자 */
					final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
					/* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
					final String saveName = getRandomString() + "." + extension;

					/* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
					File target = new File(uploadPath, saveName);
					file.transferTo(target);

					/* 파일 정보 저장 */
					BoardFile bf = new BoardFile();
					bf.setBoard(boardRepository.findById(b_no).orElseThrow(()->{
						return new IllegalArgumentException("글 찾기 실패: 글번호를 찾을 수 없습니다!");
					}));
					bf.setOriginal_name(file.getOriginalFilename());
					bf.setSave_name(saveName);
					bf.setSize((int)file.getSize());

					/* 파일 정보 추가 */
					fileList.add(bf);

				}
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} // end of for

		return fileList;
	}
 
}
