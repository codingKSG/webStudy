package com.cos.blog.service;

import com.cos.blog.domain.user.User;
import com.cos.blog.domain.user.UserDao;
import com.cos.blog.domain.user.dto.JoinReqDto;
import com.cos.blog.domain.user.dto.LoginReqDto;
import com.cos.blog.domain.user.dto.UpdateReqDto;

public class UserService {
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	// 회원가입, 로그인, 정보수정, 아이디중복체크
	
	public int 회원가입(JoinReqDto dto) {
		int result = userDao.save(dto);
		return result;
	}
	
	public User 로그인(LoginReqDto dto) {
		
		return userDao.findByUsernameAndPassword(dto);
	}
	
	public int 정보수정(UpdateReqDto dto) {
		
		return -1;
	}
	
	public int 유저네임중복체크(String username) {
		int result = userDao.findByUsername(username);
		return result;
	}
}
