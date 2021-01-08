package com.cos.blog.service;

import java.util.List;

import com.cos.blog.domain.board.Board;
import com.cos.blog.domain.board.BoardDao;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;

public class BoardService {

	private BoardDao boardDao;

	public BoardService() {
		boardDao = new BoardDao();
	}

	public int 글쓰기(SaveReqDto dto) {
		return boardDao.save(dto);
	}
	
	public List<Board> 글목록보기(int page){
		
		return boardDao.findAll(page);
	}
	
	public DetailRespDto 상세보기(int id){
		
		return boardDao.findById(id);
	}
	
	public void 조회수(int id) {
		
		boardDao.updateReadCount(id);
	}
	
	public int 글개수() {
		
		return boardDao.count();
	}
}
