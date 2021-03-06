package com.cos.blog.domain.reply.dto;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindByIdRespDto {
	private int id;
	private int userId;
	private int boardId;
	private String content;
	private String username;
	private Timestamp createDate;
}
