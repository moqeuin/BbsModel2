package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;

public class BbsDao {
	private static BbsDao dao = new BbsDao();
	
	private BbsDao() {}
	
	public static BbsDao getInstance() {
		return dao;
	}
	
	
	
	// 모든 게시물을 출력
	public List<BbsDto> getBbslist(){
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, "
				   + " TITLE, CONTENT, WDATE, "
				   + " DEL, READCOUNT "
				   + " FROM BBS "
				   + " ORDER BY REF DESC, STEP ASC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbslist success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getBbslist success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbslist success");
			
			while(rs.next()) {
				int i = 1;
				
				int seq = rs.getInt(i++);
				String id = rs.getString(i++);
				
				int ref = rs.getInt(i++);
				int step = rs.getInt(i++);
				int depth = rs.getInt(i++);
				
				String title = rs.getString(i++);
				String content = rs.getString(i++);
				String wdate = rs.getString(i++);
				
				int del = rs.getInt(i++);
				int readcount = rs.getInt(i++);
				
				BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, del, readcount);
				list.add(dto);
			}
			System.out.println("4/6 getBbslist success");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		
		
		return list;
	}
	
	
	
	// 검색 게시물 출력
	public List<BbsDto> getSelBbslist(String sel, String keyword){
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, "
				   + " TITLE, CONTENT, WDATE, "
				   + " DEL, READCOUNT "
				   + " FROM BBS ";
		String select = "";
		
		
		if(sel.equals("TITLE")) {
			select += "WHERE TITLE LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
			
		}
		else if(sel.equals("CONTENT")) {
			
			select += "WHERE CONTENT LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		else if(sel.equals("ID")) {
			
			select += "WHERE ID LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		
		sql += select;
		sql += " ORDER BY REF DESC, STEP ASC ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getSelBbslist success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getSelBbslist success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getSelBbslist success");
			
			while(rs.next()) {
				int i = 1;
				
				int seq = rs.getInt(i++);
				String id = rs.getString(i++);
				
				int ref = rs.getInt(i++);
				int step = rs.getInt(i++);
				int depth = rs.getInt(i++);
				
				String title = rs.getString(i++);
				String content = rs.getString(i++);
				String wdate = rs.getString(i++);
				
				int del = rs.getInt(i++);
				int readcount = rs.getInt(i++);
				
				BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, del, readcount);
				list.add(dto);
			}
			System.out.println("4/6 getSelBbslist success");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		
		
		return list;
	}
	
	
	
	// 선택한 페이지 게시물 출력
	public List<BbsDto> getPagingBbslist(String sel, String keyword, int currPage){
		
				
		String sql = " SELECT SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT "
				   + " FROM "
				   + " (SELECT ROW_NUMBER()OVER(ORDER BY REF DESC, STEP ASC) AS RNUM, "
				   + " SEQ, ID, REF, STEP, DEPTH, TITLE, CONTENT, WDATE, DEL, READCOUNT " 
				   + " FROM BBS ";
								   
	
		String select = "";
		
		
		if(sel.equals("TITLE")) {
			select += "WHERE TITLE LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
			
		}
		else if(sel.equals("CONTENT")) {
			
			select += "WHERE CONTENT LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		else if(sel.equals("ID")) {
			
			select += "WHERE ID LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		
		sql += select;
		sql += " ORDER BY REF DESC, STEP ASC) ";
		sql += " WHERE RNUM >= ? AND RNUM <=? ";

		int start = 1 + 10 *currPage; // 1, 11, 21
		int end = 10 + 10*currPage; // 10, 20, 30
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<BbsDto> list = new ArrayList<BbsDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getSelBbslist success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, start);
			psmt.setInt(2, end);
			System.out.println("2/6 getSelBbslist success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getSelBbslist success");
			
			while(rs.next()) {
				int i = 1;
				
				int seq = rs.getInt(i++);
				String id = rs.getString(i++);
				
				int ref = rs.getInt(i++);
				int step = rs.getInt(i++);
				int depth = rs.getInt(i++);
				
				String title = rs.getString(i++);
				String content = rs.getString(i++);
				String wdate = rs.getString(i++);
				
				int del = rs.getInt(i++);
				int readcount = rs.getInt(i++);
				
				BbsDto dto = new BbsDto(seq, id, ref, step, depth, title, content, wdate, del, readcount);
				list.add(dto);
			}
			System.out.println("4/6 getSelBbslist success");
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		
		
		return list;
	}
	
	
	
	// 게시물 추가
	public boolean writeBbs(String id, String title, String content) {
		
		
		String sql = " INSERT INTO BBS "
				+	 " (SEQ, ID, "
				+	 " REF, STEP, DEPTH, "
				+    " TITLE, CONTENT, WDATE, "
				+	 " DEL, READCOUNT) "
				+	 " VALUES( "
				+    " SEQ_BBS.NEXTVAL, ?, "
				+    " (SELECT NVL(MAX(REF), 0) + 1 FROM BBS), 0, 0, "
				+	 "  ?, ?, SYSDATE, 0, 0) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 writeBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, title);
			psmt.setString(3, content);
			System.out.println("2/6 writeBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 writeBbs success");
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, null);
		}
	return count > 0? true:false;
		
	}
	
	
	
	// 게시물의 갯수 전달
	public int getBbsAmount(String sel, String keyword) {
		
		String sql = " SELECT COUNT(*) "
				+	 " FROM BBS ";
		String select = "";
		
		
		if(sel.equals("TITLE")) {
			select += "WHERE TITLE LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
			
		}
		else if(sel.equals("CONTENT")) {
			
			select += "WHERE CONTENT LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		else if(sel.equals("ID")) {
			
			select += "WHERE ID LIKE " + "'%" + keyword + "%'" + " AND DEL = 0 ";
		}
		
		sql += select;
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsAmount success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getBbsAmount success");
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				
				count = rs.getInt(1);
			}
			System.out.println("3/6 getBbsAmount success");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		return count;
	}
	
	
	
	// 게시물의 상세페이지
	public BbsDto getDetailBbs(int seq) {
		
		String sql = " SELECT SEQ, READCOUNT, ID, TITLE, CONTENT, WDATE "
				+	 " FROM BBS "
				+	 " WHERE SEQ = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		BbsDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getDetailBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 getDetailBbs success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getDetailBbs success");
			
			if(rs.next()) {
				int i =1;
				
				int _seq = rs.getInt(i++);
				int readcount = rs.getInt(i++);
				
				String id = rs.getString(i++);
				String title= rs.getString(i++);
				String content = rs.getString(i++);
				String wdate = rs.getString(i++);
				
				dto = new BbsDto(_seq, id, title, content, wdate, readcount);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		return dto;
		
	}
	
	
	
	// 게시물 수정
	public boolean updateBbs(int seq, String title, String content) {
		
		String sql = " UPDATE BBS "
				+	 " SET TITLE = ?, CONTENT= ? "
				+	 " WHERE SEQ = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0 ;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 updateBbs success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, title);
			psmt.setString(2, content);
			psmt.setInt(3, seq);
			System.out.println("2/6 updateBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 updateBbs success");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, null);
		}
		
		
		return count > 0?true:false;		
	}
	
	
	
	// 게시물 삭제
	public boolean deleteBbs(int seq) {
		
		String sql = " UPDATE BBS "
				+	 " SET DEL =1 "
				+	 " WHERE SEQ =? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0 ;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 deleteBbs success");
			
			psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, seq);
			System.out.println("2/6 deleteBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 deleteBbs success");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, null);
		}
		
		
		return count > 0?true:false;		
	}
	
	
	
	// 댓글 입력
	public boolean writeReplyBbs(int seq, BbsDto dto) {
		
		String sql1 = " UPDATE BBS "
				+	  " SET STEP = STEP +1 "
				+	  " WHERE REF = (SELECT REF FROM BBS WHERE SEQ =?) "
				+	  " AND STEP > (SELECT STEP FROM BBS WHERE SEQ =?) ";
		
		String sql2 = " INSERT INTO BBS "
				+	  " (SEQ, ID,"
				+	  "  REF, STEP, DEPTH, "
				+	  "  TITLE, CONTENT, WDATE, DEL, READCOUNT)" 
				+	  "  VALUES"
				+	  " (SEQ_BBS.NEXTVAL, ?, "
				+	  " (SELECT REF FROM BBS WHERE SEQ =?), "
				+	  " (SELECT STEP FROM BBS WHERE SEQ =?) +1, "
				+	  " (SELECT DEPTH FROM BBS WHERE SEQ =?) +1, "
				+	  " ?, ?, SYSDATE, 0, 0) ";
		
		
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0 ;
		
		try {
			conn = DBConnection.getConnection();
			// 쿼리문을 하나를 적용할때마다 자동으로 커밋하는 기능을 정지시킨다.
			conn.setAutoCommit(false);
			System.out.println("1/6 replyBbs success");
			
			psmt = conn.prepareStatement(sql1);		
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("2/6 replyBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 replyBbs success");
			
			// 앞에서 적용된 쿼리문을 다시 초기화 시켜준다.
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, dto.getTitle());
			psmt.setString(6, dto.getContent());
			System.out.println("4/6 replyBbs success");
			
			count = psmt.executeUpdate();
			System.out.println("5/6 replyBbs success");
			
			// 쿼리문이 전부 실행됐다면 커밋을 해준다.
			conn.commit();
					
		} catch (Exception e) {
			
			e.printStackTrace();
			try {
				//catch 안에다가 롤백 생성
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			try {
				// finally 안에다가 오토커밋 생성
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			DBClose.close(psmt, conn, null);
			System.out.println("6/6 replyBbs success");
		}
		return count > 0?true:false;
	}
	
	
	
	// 상세페이지 방문시 조회수 증가
	public void getReadcount(int seq) {
		String sql = " UPDATE BBS "
				+	 " SET READCOUNT = READCOUNT + 1 "
				+	 " WHERE SEQ =? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getReadcount success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 getReadcount success");
			
			psmt.executeUpdate();
			System.out.println("3/6 getReadcount success");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, null);
		}		
	}
	
	
	
}
