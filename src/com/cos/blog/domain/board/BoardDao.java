package com.cos.blog.domain.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.blog.config.DB;
import com.cos.blog.domain.board.dto.DetailRespDto;
import com.cos.blog.domain.board.dto.SaveReqDto;
import com.cos.blog.domain.board.dto.UpdateReqDto;

public class BoardDao {

	public int save(SaveReqDto dto) {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO board(userId, title, content, createDate) ");
		sb.append("VALUES(?, ?, ?, now())");
		String sql = sb.toString();

		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getUserId());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());

			int result = pstmt.executeUpdate();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int count() {
		String sql = "SELECT COUNT(*) FROM board";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}

	public int count(String keyword) {
		String sql = "SELECT COUNT(*) FROM board WHERE title like ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return -1;
	}

	public List<Board> findAll(int page) {
		// SELECT해서 Board 객체를 컬렉션에 담아서 리턴
		List<Board> boards = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, userId, title, content, readCount, createDate");
		sb.append(" FROM board ORDER BY id DESC LIMIT ?, 4");
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, page * 4);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board board = Board.builder().id(rs.getInt("id")).userId(rs.getInt("userId"))
						.title(rs.getString("title")).content(rs.getString("content")).readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate")).build();

				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public List<Board> findByKeyword(String keyword, int page) {
		// SELECT해서 Board 객체를 컬렉션에 담아서 리턴
		List<Board> boards = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT id, userId, title, content, readCount, createDate");
		sb.append(" FROM board WHERE title like ? ORDER BY id DESC LIMIT ?, 4");
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, page * 4);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Board board = Board.builder().id(rs.getInt("id")).userId(rs.getInt("userId"))
						.title(rs.getString("title")).content(rs.getString("content")).readCount(rs.getInt("readCount"))
						.createDate(rs.getTimestamp("createDate")).build();

				boards.add(board);
			}
			return boards;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int updateReadCount(int id) {
		String sql = "update board set readCount=readCount+1 where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public DetailRespDto findById(int id) {
		// SELECT해서 Board 객체를 컬렉션에 담아서 리턴
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT b.id, b.userId, b.title, b.content, b.readCount, b.createDate, u.username");
		sb.append(" FROM board b INNER JOIN user u ON b.userId = u.id WHERE b.id = ?");
		String sql = sb.toString();
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				DetailRespDto detailRespDto = DetailRespDto.builder().id(rs.getInt("b.id"))
						.userId(rs.getInt("b.userId")).title(rs.getString("b.title")).content(rs.getString("b.content"))
						.readCount(rs.getInt("b.readCount")).createDate(rs.getTimestamp("b.createDate"))
						.username(rs.getString("u.username")).build();

				return detailRespDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt, rs);
		}
		return null;
	}

	public int deleteById(int id) {
		String sql = "DELETE FROM board where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DB.close(conn, pstmt);
		}
		return -1;
	}

	public int update(UpdateReqDto updateReqDto) {

		String sql = "UPDATE board SET title = ?, content = ? WHERE id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updateReqDto.getTitle());
			pstmt.setString(2, updateReqDto.getContent());
			pstmt.setInt(3, updateReqDto.getId());

			int result = pstmt.executeUpdate();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // 무조건 실행
			DB.close(conn, pstmt);
		}

		return -1;
	}
}
