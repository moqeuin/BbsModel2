package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBClose;
import db.DBConnection;
import dto.BbsDto;
import dto.MemberDto;

public class MemberDao {
	
	private static MemberDao dao = new MemberDao();
	
	private MemberDao() {
		DBConnection.initConnection();
	}
	
	public static MemberDao getInstance() {
		
		return dao;
		
	}
	
	// id 중복확인
	public boolean checkId(String id) {
		String sql = " SELECT ID "
				+	 " FROM MEMBER "
				+	 " WHERE ID =? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		boolean ch = false;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 checkId success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			System.out.println("2/6 checkId success");
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				
				ch = true;
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		
		return ch;
	}
	
	
	
	// 회원가입
	public boolean regiMember(String id, String pwd, String addr, String email) {
		String sql = " INSERT INTO MEMBER "
				+	 " (ID, PWD, NAME, EMAIL, AUTH) "
				+	 " VALUES(?, ?, ?, ?, 3) ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 regiMember success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			psmt.setString(3, addr);
			psmt.setString(4, email);
			System.out.println("2/6 regiMember success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 regiMember success");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, null);
		}
		return count > 0?true:false;
		
	}
			
	// 아이디와 패스워드 확인
	// 맞으면 회원정보를 객체로 전달
	public MemberDto loginMember(String id, String pwd) {
		String sql = " SELECT ID, NAME, EMAIL, AUTH "
				   + " FROM MEMBER "
				   + " WHERE ID = ? AND PWD = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		MemberDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 loginMember success");

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			psmt.setString(2, pwd);
			
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				
				String _id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String auth = rs.getString(4);
				dto = new MemberDto(_id, null, name, email, auth);
				
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			DBClose.close(psmt, conn, rs);
		}
		return dto;
		
		
	}
	
	
	
	
}
